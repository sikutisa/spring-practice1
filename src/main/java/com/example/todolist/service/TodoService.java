package com.example.todolist.service;

import com.example.todolist.model.TodoEntity;
import com.example.todolist.model.TodoRequest;
import com.example.todolist.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService
{
    private final TodoRepository todoRepository;

    // todolist에 item 추가
    public TodoEntity add(TodoRequest todoRequest)
    {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(todoRequest.getTitle());
        todoEntity.setOrder(todoRequest.getOrder());
        todoEntity.setCompleted(todoRequest.getCompleted());

        return this.todoRepository.save(todoEntity);
    }

    // todolist에서 특정 item 조회
    public TodoEntity searchById(Long id)
    {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // todolist에서 전체 목록 조회
    public List<TodoEntity> searchAll()
    {
        return this.todoRepository.findAll();
    }

    // todolist에서 특정 item 수정
    public TodoEntity updateById(Long id, TodoRequest todoRequest)
    {
        TodoEntity todoEntity = this.searchById(id);
        if(todoRequest.getTitle() != null)
        {
            todoEntity.setTitle(todoRequest.getTitle());
        }
        if(todoRequest.getOrder() != null)
        {
            todoEntity.setOrder(todoRequest.getOrder());
        }
        if(todoRequest.getCompleted() != null)
        {
            todoEntity.setCompleted(todoRequest.getCompleted());
        }

        return this.todoRepository.save(todoEntity);
    }

    // todolist에서 특정 item 삭제
    public void deleteById(Long id)
    {
        this.todoRepository.deleteById(id);
    }
    
    // todolist에서 전체 목록 삭제
    public void deleteAll()
    {
        this.todoRepository.deleteAll();
    }
}
