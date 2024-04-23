package com.example.ms1.Notebook;

import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteRepository;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class NotebookController {
    private final NotebookRepository notebookRepository;
    private final NoteRepository noteRepository;
    private final NoteService noteService;

    @PostMapping("/write")
    public String write() {
        Notebook notebook = new Notebook();
        notebook.setName("새 노트북");
        notebookRepository.save(notebook);
        noteService.saveDefault(notebook);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id) {
        Notebook targetNotebook = notebookRepository.findById(id).orElseThrow();
        Note note = noteRepository.findByNotebook(targetNotebook).get(0);

        return "redirect:/books/%d/notes/%d".formatted(id, note.getId());

    }
}
