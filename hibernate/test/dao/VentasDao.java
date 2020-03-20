package hibernate.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import hibernate.test.HibernateUtil;
import hibernate.test.dto.PersonaEntity;
import hibernate.test.dto.VentasEntity;


public class VentasDao {
	public void insertVenta(float importe) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(importe);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public String setImporte(float importe) {
		// TODO Auto-generated method stub
		return null;
	}

}
