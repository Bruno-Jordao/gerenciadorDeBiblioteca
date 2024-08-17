package Model;

import Interfaces.Observer;

public class Observador implements Observer {

    private String nome;

    public Observador(String nome) {
        this.nome = nome;
    }

    @Override
    public void update(String mensagem) {
        System.out.println(nome + " recebeu a mensagem: " + mensagem);
    }
}
