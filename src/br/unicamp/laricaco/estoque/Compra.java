package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.utilidades.LariCACoException;
import br.unicamp.laricaco.usuario.Usuario;

import java.io.*;
import java.util.Date;

public class Compra extends TransacaoEstoque {

    Compra(GerenciadorEstoque gerenciadorEstoque, Usuario usuario, Date data) {
        super(Tipo.COMPRA, gerenciadorEstoque, usuario, data);
    }

    @Override
    public void adicionarProduto(Produto produto, int quantidade) throws LariCACoException {
        /* Caso o produto já tenha sido adicionado, o cálculo de getEstoque já terá incluído o valor anterior, então
         * não precisamos calcular novamente */
        if (quantidade > produto.getEstoque()) {
            throw new LariCACoException("Não há produtos suficientes no estoque!");
        }
        super.adicionarProduto(produto, quantidade);
    }

    @Override
    public float getValor(Produto produto) {
        return produtos.getOrDefault(produto, 0) * produto.getPrecoVenda();
    }

    public int quantidadeProduto(Produto produto){
        return produtos.getOrDefault(produto, 0);
    }

    @Override
    public String toString() {
        return "Compra realizada na data " + getData() + " por " + getUsuario() + " no valor de R$" + getValor();
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        super.salvar(outputStream);
        outputStream.flush();
    }
}
