package br.com.filipepinato.paif_logistica_2.dao;

import com.activeandroid.query.Select;

import java.util.List;

import br.com.filipepinato.paif_logistica_2.model.Compra;

public class CompraDAO {
    public List<Compra> findAll(){
        return new Select()
                .from(Compra.class)
                .orderBy("descricao ASC")
                .execute();
    }

}
