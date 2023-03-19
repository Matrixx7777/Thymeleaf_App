package com.app.application.service;

import java.util.List;
import java.util.Optional;

import com.app.application.domain.Owner;
import com.app.application.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void saveOwner(Owner owner) {
        this.ownerRepository.save(owner);
    }

    public Owner getOwnerById(long id) {
        Optional<Owner> optional = ownerRepository.findById(id);
        Owner owner;
        if (optional.isPresent()) {
            owner = optional.get();
        } else {
            throw new RuntimeException(" Owner not found for id :: " + id );
        }
        return owner;
    }

    public void deleteOwnerById(long id) {
        this.ownerRepository.deleteById(id);
    }

    public Page<Owner> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.ownerRepository.findAll(pageable);
    }
}