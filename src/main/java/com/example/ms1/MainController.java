package com.example.ms1;

import com.example.ms1.Notebook.Notebook;
import com.example.ms1.Notebook.NotebookRepository;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteRepository;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final NoteService noteService;
    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;

    @RequestMapping("/")
    public String main(Model model) {
        List<Notebook> notebookList = notebookRepository.findAll();
        if (notebookList.isEmpty()) {
            Notebook notebook = new Notebook();
            notebook.setName("새 노트북");
            notebookRepository.save(notebook);

            return "redirect:/";
        }
        Notebook targetNotebook = notebookList.get(0);
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);
        if (noteList.isEmpty()) {
            noteService.saveDefault(targetNotebook);

            return "redirect:/";
        }

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);
        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));

        return "main";
    }

}
