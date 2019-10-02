package konkon.service;

import java.util.List;

public interface GeneralService<E> {
  List<E> findAll();
  E findById(Long id);
  List<E> findByName(String name);
  void delete(Long id);
  void add(E e);
  void update(E e);
}
