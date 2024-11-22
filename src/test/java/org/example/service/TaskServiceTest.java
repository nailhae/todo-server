package org.example.service;

import org.example.constants.TaskStatus;
import org.example.model.Task;
import org.example.persist.TaskRepository;
import org.example.persist.entity.TaskEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;

	@Test
	@DisplayName("할 일 추가 기능 테스트")
	void add() {
		String title = "test";
		String description = "test description";
		LocalDate dueDate = LocalDate.now();

		when(taskRepository.save(any(TaskEntity.class)))
				.thenAnswer(invocationOnMock -> {
					TaskEntity e = (TaskEntity) invocationOnMock.getArgument(0);
					e.setId(1L);
					e.setCreatedAt(new Timestamp(System.currentTimeMillis()));
					e.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
					return e;
				});

		Task actual = taskService.add(title, description, dueDate);

		verify(taskRepository, times(1)).save(any());

		assertEquals(1L, actual.getId());
		assertEquals(title, actual.getTitle());
		assertEquals(description, actual.getDescription());
		assertEquals(TaskStatus.TODO, actual.getStatus());
		assertNotNull(actual.getCreatedAt());
		assertNotNull(actual.getUpdatedAt());
	}
}