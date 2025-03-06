package app.service;

import app.entity.Owner;
import app.exeption.ResourceNotFoundException;
import app.messages.ErrorMessages;
import app.repository.OwnerRepository;
import app.utils.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public String save(Owner owner) {

        boolean isIncomplete = false;

        //  Remove caracteres não numéricos do CPF
        String cpf = owner.getCpf().replaceAll("[^0-9]", "");

        //   valida o CPF
        if (!CpfValidator.isValidCPF(cpf)) {
            return "CPF inválido";
        }

        //  verifica corretamente o telefone e o e-mail
        if ((owner.getPhone() == null || owner.getPhone().isEmpty()) ||
                (owner.getEmail() == null || owner.getEmail().isEmpty())) {
            isIncomplete = true;
        }

        //Define o status de cadastro
        owner.setStatusRegister(isIncomplete ? "INCOMPLETO" : "COMPLETO");


        this.ownerRepository.save(owner);

        return isIncomplete
                ? "Cadastro incompleto! Quando possível, atualize o cadastro com e-mail ou telefone."
                : "Proprietário criado com sucesso!";
    }

    public Owner findById(Long id) {
    return ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário com ID " + id + " não foi encontrado"));
}

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public String update(Long id, Owner owner) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        if (ownerOptional.isPresent()) {
            Owner existingOwner = ownerOptional.get();
            if (owner.getName() != null) existingOwner.setName(owner.getName());
            if (owner.getCpf() != null) existingOwner.setCpf(owner.getCpf());
            if (owner.getEmail() != null) existingOwner.setEmail(owner.getEmail());
            if (owner.getPhone() != null) existingOwner.setPhone(owner.getPhone());

            this.ownerRepository.save(existingOwner);

            return "Propietaio atualizado com sucesso";

        } else {
            return "Propietatio com " +id + " não encontrado";
        }
    }

    public String delete(Long id) {
    Optional<Owner> ownerOptional = ownerRepository.findById(id);
    if (ownerOptional.isPresent()) {
        this.ownerRepository.deleteById(id);
        return "Propietario deletado com sucesso";
    } else {
        return "Propietario com " + id + " não encontrado";
    }
}

    public List<Owner> findByName(String name) {
        List <Owner> ownerList = ownerRepository.findByName(name);
        if (ownerList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + name + " name");
        }
        return this.ownerRepository.findByName(name);
    }

    public List<Owner> findByEmail(String email) {
        List <Owner> ownerList = ownerRepository.findByEmail(email);
        if (ownerList.isEmpty()) {
            throw new RuntimeException(ErrorMessages.OWNER_NOT_FOUND + email + " email");
        }
        return this.ownerRepository.findByEmail(email);
    }
    

}