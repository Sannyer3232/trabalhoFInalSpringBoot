package aranoua.edu.atividadeFinal.service;

import aranoua.edu.atividadeFinal.dto.AfiliacaoInputDTO;
import aranoua.edu.atividadeFinal.dto.AfiliacaoOutputDTO;
import aranoua.edu.atividadeFinal.model.Afiliacao;
import aranoua.edu.atividadeFinal.repository.AfiliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AfiliacaoService {

    @Autowired
    private AfiliacaoRepository afiliacaoRepository;

    public List<AfiliacaoOutputDTO> list(){
        List<Afiliacao> lista = afiliacaoRepository.findAll();
        List<AfiliacaoOutputDTO> listaDTO = new ArrayList<>();

        for (Afiliacao afiliacao : lista) {
            listaDTO.add(new AfiliacaoOutputDTO(afiliacao));
        }

        return listaDTO;
    }

    public AfiliacaoOutputDTO getById(Long id){
        Optional<Afiliacao> afiliacao = afiliacaoRepository.findById(id);
        return new AfiliacaoOutputDTO(afiliacao.get());
    }

    public AfiliacaoOutputDTO create(AfiliacaoInputDTO afiliacaoInputDTO){

        try {
            Afiliacao afiliacao = afiliacaoInputDTO.build();
            afiliacaoRepository.save(afiliacao);
            return new AfiliacaoOutputDTO(afiliacao);
        }catch (Exception e){
            return null;
        }
    }

    public AfiliacaoOutputDTO update(Long id,AfiliacaoInputDTO afiliacaoInputDTO){
        try {
            Optional<Afiliacao> possivelAfiliacao = afiliacaoRepository.findById(id);

            if(possivelAfiliacao.isPresent()){
                Afiliacao afiliacao = possivelAfiliacao.get();
                afiliacao.setNome(afiliacaoInputDTO.getNome());
                afiliacao.setReferencia(afiliacaoInputDTO.getReferencia());
                afiliacao.setSigla(afiliacaoInputDTO.getSigla());
                return new AfiliacaoOutputDTO(afiliacaoRepository.save(afiliacao));
            }else{
                return null;
            }

        }catch (Exception e){
            return null;
        }
    }

    public Boolean  delete(Long id){
        try{
            Optional<Afiliacao> possivelAfiliacao = afiliacaoRepository.findById(id);
            if(possivelAfiliacao.isPresent()){
                Afiliacao afiliacao = possivelAfiliacao.get();
                afiliacaoRepository.delete(afiliacao);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
