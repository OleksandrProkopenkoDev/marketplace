package ua.tc.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.service.DemoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

  private final DemoService demoService;

  @GetMapping
  public String demo() {
    return "Hello from backend!";
  }

  @GetMapping("/all")
  public ResponseEntity<Page<DemoRequest>> getAllDemo(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(demoService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DemoRequest> getById(@PathVariable("id") Integer id){
    DemoRequest demoRequest = demoService.findById(id);
    return ResponseEntity.ok(demoRequest);
  }
}
