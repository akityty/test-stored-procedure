package konkon.repository;

import java.util.List;

public interface GeneralRepository<E> {
  List<E> findAll();
  E findById(Long id);
  List<E> findByName(String name);
  void delete(Long id);
  void add(E e);
  void update(E e);
}
