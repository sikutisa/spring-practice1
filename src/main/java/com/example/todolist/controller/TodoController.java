package com.example.todolist.controller;

import com.example.todolist.model.TodoEntity;
import com.example.todolist.model.TodoRequest;
import com.example.todolist.model.TodoResponse;
import com.example.todolist.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/")
public class TodoController
{
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest todoRequest)
    {
        if(ObjectUtils.isEmpty(todoRequest.getTitle()))
            return ResponseEntity.badRequest().build();

        if(ObjectUtils.isEmpty(todoRequest.getOrder()))
            todoRequest.setOrder(0L);

        if(ObjectUtils.isEmpty(todoRequest.getCompleted()))
            todoRequest.setCompleted(false);

        TodoEntity result = this.todoService.add(todoRequest);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id)
    {
        TodoEntity result  = this.todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll()
    {
        List<TodoEntity> list  = this.todoService.searchAll();
        List<TodoResponse> responses = list.stream().map(TodoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest todoRequest)
    {
        this.todoService.updateById(id, todoRequest);
        TodoEntity todoEntity = this.todoService.updateById(id, todoRequest);
        return ResponseEntity.ok(new TodoResponse(todoEntity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id)
    {
        this.todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll()
    {
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
