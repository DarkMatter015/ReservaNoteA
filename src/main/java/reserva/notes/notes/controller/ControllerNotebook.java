package reserva.notes.notes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reserva.notes.notes.exception.RegistroNaoEncontradoException;
import reserva.notes.notes.model.ModelNotebook;
import reserva.notes.notes.service.ServiceNotebook;

import java.util.List;

@Controller
@RequestMapping("/notebook")
public class ControllerNotebook {

    @Autowired
    ServiceNotebook notebookServico;

    @GetMapping("/")
    public String listarNotebooks(Model model) {
        List<ModelNotebook> notebooks = notebookServico.listarNotebooks();
        model.addAttribute("listaNotebooks",notebooks);
        return "/lista-notebooks";
    }

    @GetMapping("/novo")
    public String novoNotebook(Model model) {
        ModelNotebook notebook = new ModelNotebook();
        model.addAttribute("objetoNotebook",notebook);
        return "/edita-notebook";
    }

    @PostMapping("/gravar")
    public String gravarNotebook(@ModelAttribute("novoNotebook") @Valid ModelNotebook notebook,
                                  BindingResult erros,
                                  RedirectAttributes attributes) {
        if(erros.hasErrors()) {
            return "/novo-notebook";
        }
        notebookServico.salvarNotebook(notebook);
        attributes.addFlashAttribute("mensagem", "Notebook salvo com sucesso!");
        return "redirect:/novo";
    }

    @GetMapping("/apagar/{id}")
    public String apagarNotebook(@PathVariable("id") long id, RedirectAttributes attributes) {
        try {
            notebookServico.apagarNotebook(id);
        } catch (RegistroNaoEncontradoException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/";
    }


    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") long id, RedirectAttributes attributes,
                             Model model) {
        try {
            ModelNotebook notebook = notebookServico.buscarNotebookPorId(id);
            model.addAttribute("objetoNotebook", notebook);
            return "/alterar-notebook";
        } catch (RegistroNaoEncontradoException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/editar/{id}")
    public String editarNotebook(@PathVariable("id") long id,
                                  @ModelAttribute("objetoNotebook") @Valid ModelNotebook notebook,
                                  BindingResult erros) {
        if (erros.hasErrors()) {
            notebook.setId(id);
            return "/alterar-notebook";
        }
        notebookServico.salvarNotebook(notebook);
        return "redirect:/";
    }
    
}
