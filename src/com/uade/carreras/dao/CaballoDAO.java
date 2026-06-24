package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.dto.RankingDTO;
import com.uade.carreras.model.Caballo;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CaballoDAO {

    public List<Caballo> listarCaballos() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("select c from Caballo c order by c.id", Caballo.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("No se pudo leer el catálogo de caballos: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<RankingDTO> rankingCaballos() {
        List<RankingDTO> ranking = new ArrayList<>();
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            List<Object[]> filas = em.createQuery(
                    "select c.nombre, c.tipo, coalesce(sum(p.puntos), 0) "
                    + "from Caballo c left join Posicion p on p.caballo = c "
                    + "group by c.id, c.nombre, c.tipo "
                    + "order by coalesce(sum(p.puntos), 0) desc", Object[].class)
                    .getResultList();
            int pos = 1;
            for (Object[] fila : filas) {
                String nombre = (String) fila[0];
                String tipo = (String) fila[1];
                int total = ((Number) fila[2]).intValue();
                ranking.add(new RankingDTO(pos, nombre, tipo, total));
                pos++;
            }
        } catch (Exception e) {
            System.err.println("No se pudo leer el ranking de caballos: " + e.getMessage());
        } finally {
            em.close();
        }
        return ranking;
    }
}
