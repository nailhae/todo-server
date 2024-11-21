package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.example.constants.TaskStatus;
import org.example.model.Task;
import org.example.persist.TaskRepository;
import org.example.persist.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;

	public Task add(String title, String description, LocalDate dueDate) {
		TaskEntity e = TaskEntity.builder()
				.title(title)
				.description(description)
				.dueDate(Date.valueOf(dueDate))
				.status(TaskStatus.TODO)
				.build();

		TaskEntity saved = taskRepository.save(e);

		return entityToObject(saved);
	}

	public List<Task> getAll() {
		return taskRepository.findAll().stream()
				.map(this::entityToObject)
				.collect(Collectors.toList());
	}

	public List<Task> getByDueDate(String dueDate) {
		return taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
				.map(this::entityToObject)
				.collect(Collectors.toList());
	}

	public List<Task> getByStatus(TaskStatus status) {
		return taskRepository.findAllByStatus(status).stream()
				.map(this::entityToObject)
				.collect(Collectors.toList());
	}

	public Task getOne(Long id) {
		TaskEntity entity = getById((id));
		return entityToObject(entity);
	}

	public Task update(Long id, String title, String description, LocalDate dueDate) {
		TaskEntity exists = getById(id);

		exists.setTitle(Strings.isEmpty(title) ?
				exists.getTitle() : title);
		exists.setDescription(Strings.isEmpty(description) ?
				exists.getDescription() : description);
		exists.setDueDate(Objects.isNull(dueDate) ?
				exists.getDueDate() : Date.valueOf(dueDate));

		TaskEntity updated = taskRepository.save(exists);
		return entityToObject(updated);
	}

	public Task updateStatus(Long id, TaskStatus status) {
		TaskEntity entity = getById(id);

		entity.setStatus(status);

		TaskEntity saved = taskRepository.save(entity);
		return entityToObject(saved);
	}

	public boolean delete(Long id) {
		try {
			taskRepository.deleteById(id);
		} catch (Exception e) {
			log.error("an error occurred while deleting [{}]", e.toString());
			return false;
		}
		return true;
	}

	private TaskEntity getById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() ->
						new IllegalArgumentException(String.format("not exists task id [%d]", id)));
	}

	private Task entityToObject(TaskEntity e) {
		return Task.builder()
				.id(e.getId())
				.title(e.getTitle())
				.description(e.getDescription())
				.status(e.getStatus())
				.dueDate(e.getDueDate().toString())
				.createdAt(e.getCreatedAt().toLocalDateTime())
				.updatedAt(e.getUpdatedAt().toLocalDateTime())
				.build();
	}
}
