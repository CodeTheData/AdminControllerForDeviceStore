package SessionFactory;

//обязательно эту библиотеку
import Entity.Customer;
import Entity.Role;
import org.hibernate.cfg.Configuration;



public class HibernateRunner {
    public static void main(String[] args) {

        Configuration configuration = new Configuration(); //по сути то, что написано в файле cfg.xml
        configuration.configure();
//        configuration.addAnnotatedClass(Customer.class);
        //так хибернейт узнает о нашем классе Кастомер
        try ( var sessionFactory = configuration.buildSessionFactory();
              var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(Customer.builder()
                            .id(12)
                            .firstName("Vlad")
                            .lastName("Hoho")
                            .address("2025 street")
                            .phone("77777777777")
//                        .date(new Date())
                            .age(12)
                            .role(Role.USER)
                            .build()
            );
            session.getTransaction().commit();
        }
    }
}
