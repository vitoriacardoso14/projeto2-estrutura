/*
PROJETO 2 - BST NETFLIX
Disciplina: Estrutura de Dados

Integrantes:
- Vitória Cardoso Alves - 10735648
- Bruno Malagoli - 10736098
*/

public class Node {

    ProgramaNetFlix data;

    Node left;
    Node right;

    public Node(ProgramaNetFlix data) {

        this.data = data;
        this.left = null;
        this.right = null;
    }
}