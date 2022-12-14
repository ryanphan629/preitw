package com.safetrust.contact.controller;

import com.safetrust.contact.constant.ResponseCode;
import com.safetrust.contact.dto.ResponseDto;
import com.safetrust.contact.dto.contact.ContactDto;
import com.safetrust.contact.dto.contact.CreateContactDto;
import com.safetrust.contact.dto.contact.UpdateContactDto;
import com.safetrust.contact.dto.paging.PagingDto;
import com.safetrust.contact.entity.Contact;
import com.safetrust.contact.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public PagingDto<Contact> list(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return new PagingDto<>(ResponseCode.OK, service.getList(page, pageSize));
    }

    @PostMapping
    public ResponseDto<ContactDto> createNewContact(@Valid @RequestBody CreateContactDto dto) {
        ContactDto response = service.createNew(dto);
        return new ResponseDto<>(ResponseCode.CREATED, response);
    }

    @GetMapping("/{id}")
    public ResponseDto<ContactDto> getDetail(@PathVariable Long id) {
        return ResponseDto.ok(service.getDetail(id));
    }

    @PutMapping("/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @Valid @RequestBody UpdateContactDto dto) {
        service.update(id, dto);
        return ResponseDto.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseDto.ok();
    }

    @GetMapping("/search")
    public ResponseDto<List<ContactDto>> search(@RequestParam(value = "name", required = false) String name) {
        List<ContactDto> contacts = service.findContact(name);
        return ResponseDto.ok(contacts);
    }
}
