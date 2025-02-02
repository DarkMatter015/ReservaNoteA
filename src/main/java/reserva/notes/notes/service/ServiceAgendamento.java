package reserva.notes.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reserva.notes.notes.exception.RegistroNaoEncontradoException;
import reserva.notes.notes.model.ModelAgendamento;
import reserva.notes.notes.repo.RepoAgendamento;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceAgendamento {
    @Autowired
    private RepoAgendamento repoAgendamento;
    public ModelAgendamento salvarAgendamentos(ModelAgendamento agendamentos){
        return repoAgendamento.save(agendamentos);
    }
    public List<ModelAgendamento> listarAgendamentos(){
        return repoAgendamento.findAll();
    }

    public ModelAgendamento buscarAgendamentoPorId(Long id)throws RegistroNaoEncontradoException {
        Optional<ModelAgendamento> opt = repoAgendamento.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new RegistroNaoEncontradoException("Agendamento com id : " + id + " não existe");
        }
    }

    public void apagarAgendamento(Long id) throws RegistroNaoEncontradoException {
        ModelAgendamento agendamento = buscarAgendamentoPorId(id);
        repoAgendamento.delete(agendamento);
    }


    public ModelAgendamento salvarAgendamento(ModelAgendamento agendamento){
        return repoAgendamento.save(agendamento);
    }

}
