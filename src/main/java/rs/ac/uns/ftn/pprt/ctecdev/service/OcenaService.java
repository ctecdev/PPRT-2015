package rs.ac.uns.ftn.pprt.ctecdev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.pprt.ctecdev.model.Ocena;
import rs.ac.uns.ftn.pprt.ctecdev.repository.OcenaRepository;

@Service
public class OcenaService {

	@Autowired
	OcenaRepository ocenaRepository;
	
	public Ocena findOne(Integer id) {
		return ocenaRepository.findOne(id);
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}

	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public void remove(Integer id) {
		ocenaRepository.delete(id);
	}
	
}
