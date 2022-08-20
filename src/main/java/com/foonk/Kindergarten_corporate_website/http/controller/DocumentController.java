package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.dto.DocumentCreateEditDto;
import com.foonk.Kindergarten_corporate_website.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/admin/documents")
    public String admin_documents(Model model) {
        Kind[] values = Kind.values();
        List<Kind> kind_kinds = Arrays.asList(values);
        model.addAttribute("kinds", kind_kinds);
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        return "user/admin_documents";
    }

    @GetMapping("/admin/russian")
    public String findAllByKind(Model model) {
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        return "user/admin_russian";
    }

/*    @GetMapping("/admin/documents/{kind}/{id}")
    public String getDocument(Model model, @PathVariable Long id, @PathVariable String kind) {
        documentService.findDocumentById(id).map(document -> documentService.get(document.getDocument(), kind));
        return "redirect:/admin/documents";
    }*/

    @PostMapping("/admin/documents/create")
    public String admin_documents_create(Model model, DocumentCreateEditDto dto) {
        documentService.create(dto);
        return "redirect:/admin/documents";
    }

    @PostMapping("/admin/documents/delete")
    public String admin_documents_delete(Model model, Long idDocument) {
        documentService.delete(idDocument);
        return "redirect:/admin/documents";
    }
}

