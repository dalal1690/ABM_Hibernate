package hibernate.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import hibernate.test.HibernateUtil;
import hibernate.test.dto.PersonaEntity;

public class PersonaDao {
	public void insertPersona(PersonaEntity per) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public List<PersonaEntity> getAll() {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		List<PersonaEntity> perlist = new ArrayList<PersonaEntity>();
		try {
			perlist = sesn.createQuery("From PersonaEntity").list();
			System.out.println("ID | NOMBRE | EDAD | FECHA DE NACIMIENTO");
			for (PersonaEntity per : perlist) {
				System.out.println(per.getNombre() + " " + per.getEdad() + " " + per.getFechadenacimiento());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return perlist;
	}

	public PersonaEntity getPersona(int personaId) {
		Session sesn = HibernateUtil.getSessionFactory().openSession();
		PersonaEntity perToSearch = null;
		List<PersonaEntity> perToSearch1 = new ArrayList<PersonaEntity>();
		try {
			perToSearch1 = sesn.createQuery("From PersonaEntity Where ID =" + personaId).list();
			if (!perToSearch1.isEmpty()) {
				perToSearch = perToSearch1.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			sesn.close();
		}

		HibernateUtil.shutdown();
		return perToSearch;
	}

	public void updatePersona(PersonaEntity per) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.update(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public void deletePersona(PersonaEntity per) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
}
