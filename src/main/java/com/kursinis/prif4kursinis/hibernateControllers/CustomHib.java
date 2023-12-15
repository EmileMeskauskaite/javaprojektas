package com.kursinis.prif4kursinis.hibernateControllers;

import com.kursinis.prif4kursinis.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CustomHib extends GenericHib {
    public CustomHib(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public User getUserByCredentials(String login, String password) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
            Query q;

            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void deleteProduct(int id) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var product = em.find(Product.class, id);
            //Kai turiu objekta, galiu ji "nulinkint"

            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getInStockProducts().remove(product);
                em.merge(warehouse);
            }

            em.remove(product);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAvailableProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.isNull(root.get("cart")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void addToCart(int userId, List<Product> productList) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            User user = getEntityById(User.class, userId);
            Cart cart = new Cart(user);
            for (Product p : productList) {
                Product product = em.find(Product.class, p.getId()); // Retrieve the existing product from the database
                product.setCart(cart);
                cart.getItemsInCart().add(product);
            }
            user.getMyCarts().add(cart);
            em.merge(user);

            em.getTransaction().commit();
        } catch (NoResultException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
    public void deleteComment(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var comment = em.find(Comment.class, id);

            if (comment.getClass() == Review.class) {
                Review review = (Review) comment;
                Product product = em.find(Product.class, review.getProduct().getId());
                product.getReviews().remove(review);
                em.merge(product);
            } else {
                comment.getReplies().clear();
                em.remove(comment);

            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createWarehouse(String title, String address) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Warehouse warehouse = new Warehouse(title, address);
            em.persist(warehouse);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEntityById(Class<User> userClass, int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(userClass, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public User createUser(String login, String password, String name, String surname, String address) {
        EntityManager em = getEntityManager();
        User user = null;
        try {
            em.getTransaction().begin();
            Customer customer = new Customer();
            customer.setLogin(login);
            customer.setPassword(password);
            customer.setName(name);
            customer.setSurname(surname);
            em.persist(customer);
            em.getTransaction().commit();
            user = customer; // Assign the created customer to the user reference
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return user;
    }

    public Warehouse getWarehouseByCredentials(String title, String address) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Warehouse> query = cb.createQuery(Warehouse.class);
            Root<Warehouse> root = query.from(Warehouse.class);
            query.select(root).where(cb.and(cb.like(root.get("title"), title), cb.like(root.get("address"), address)));
            Query q;

            q = em.createQuery(query);
            return (Warehouse) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public User updateUser(String login, String password, String name, String surname, int userId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setSurname(surname);
                em.merge(user);
            }
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
