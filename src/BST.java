/*
PROJETO 2 - BST NETFLIX
Disciplina: Estrutura de Dados

Integrantes:
- Vitória Cardoso Alves - 10735648
- Bruno Malagoli - 10736098
*/

import java.util.LinkedList;
import java.util.Queue;

public class BST {

    // =====================================================
    // RAIZ DA ÁRVORE
    // =====================================================
    private Node root;

    // =====================================================
    // CONTADOR DE COMPARAÇÕES
    // =====================================================
    // Utilizado na busca para mostrar quantas comparações
    // foram feitas até encontrar (ou não) um programa.
    private int comparisons;

    // =====================================================
    // CONSTRUTOR
    // =====================================================
    public BST() {

        root = null;

        comparisons = 0;
    }

    // =====================================================
    // RETORNA A RAIZ DA ÁRVORE
    // =====================================================
    public Node getRoot() {

        return root;
    }

    // =====================================================
    // RETORNA A QUANTIDADE DE COMPARAÇÕES
    // =====================================================
    public int getComparisons() {

        return comparisons;
    }

    // =====================================================
    // INSERIR ELEMENTO NA BST
    // =====================================================
    public void insert(ProgramaNetFlix program) {

        root = insertRecursive(root, program);
    }

    // =====================================================
    // INSERÇÃO RECURSIVA
    // =====================================================
    private Node insertRecursive(
            Node current,
            ProgramaNetFlix program
    ) {

        // =================================================
        // SE O NÓ ESTIVER VAZIO, CRIA UM NOVO
        // =================================================
        if (current == null) {

            return new Node(program);
        }

        // =================================================
        // INSERE À ESQUERDA
        // =================================================
        if (
                program.getId()
                        .compareTo(current.data.getId()) < 0
        ) {

            current.left = insertRecursive(
                    current.left,
                    program
            );
        }

        // =================================================
        // INSERE À DIREITA
        // =================================================
        else if (
                program.getId()
                        .compareTo(current.data.getId()) > 0
        ) {

            current.right = insertRecursive(
                    current.right,
                    program
            );
        }

        return current;
    }

    // =====================================================
    // BUSCAR PROGRAMA PELO ID
    // =====================================================
    public ProgramaNetFlix search(String id) {

        // ZERA AS COMPARAÇÕES
        comparisons = 0;

        return searchRecursive(root, id);
    }

    // =====================================================
    // BUSCA RECURSIVA
    // =====================================================
    private ProgramaNetFlix searchRecursive(
            Node current,
            String id
    ) {

        // =================================================
        // NÃO ENCONTROU
        // =================================================
        if (current == null) {

            return null;
        }

        // CONTA COMPARAÇÃO
        comparisons++;

        // =================================================
        // COMPARAÇÃO ENTRE IDs
        // =================================================
        int compare =
                id.compareTo(current.data.getId());

        // =================================================
        // ENCONTROU
        // =================================================
        if (compare == 0) {

            return current.data;
        }

        // =================================================
        // PROCURA À ESQUERDA
        // =================================================
        if (compare < 0) {

            return searchRecursive(
                    current.left,
                    id
            );
        }

        // =================================================
        // PROCURA À DIREITA
        // =================================================
        return searchRecursive(
                current.right,
                id
        );
    }

    // =====================================================
    // REMOVER ELEMENTO
    // =====================================================
    public void remove(String id) {

        root = removeRecursive(root, id);
    }

    // =====================================================
    // REMOÇÃO RECURSIVA
    // =====================================================
    private Node removeRecursive(
            Node current,
            String id
    ) {

        // =================================================
        // NÃO ENCONTROU
        // =================================================
        if (current == null) {

            return null;
        }

        int compare =
                id.compareTo(current.data.getId());

        // =================================================
        // PROCURA À ESQUERDA
        // =================================================
        if (compare < 0) {

            current.left = removeRecursive(
                    current.left,
                    id
            );
        }

        // =================================================
        // PROCURA À DIREITA
        // =================================================
        else if (compare > 0) {

            current.right = removeRecursive(
                    current.right,
                    id
            );
        }

        // =================================================
        // ENCONTROU O NÓ
        // =================================================
        else {

            // =============================================
            // CASO 1 - SEM FILHOS
            // =============================================
            if (
                    current.left == null
                    &&
                    current.right == null
            ) {

                return null;
            }

            // =============================================
            // CASO 2 - APENAS FILHO DIREITO
            // =============================================
            if (current.left == null) {

                return current.right;
            }

            // =============================================
            // CASO 3 - APENAS FILHO ESQUERDO
            // =============================================
            if (current.right == null) {

                return current.left;
            }

            // =============================================
            // CASO 4 - DOIS FILHOS
            // =============================================
            // Procura o menor nó da subárvore direita
            Node smallest =
                    findSmallest(current.right);

            // Troca os dados
            current.data = smallest.data;

            // Remove o nó duplicado
            current.right = removeRecursive(
                    current.right,
                    smallest.data.getId()
            );
        }

        return current;
    }

    // =====================================================
    // PROCURA O MENOR NÓ
    // =====================================================
    private Node findSmallest(Node current) {

        while (current.left != null) {

            current = current.left;
        }

        return current;
    }

    // =====================================================
    // ALTURA DA ÁRVORE
    // =====================================================
    public int height() {

        return heightRecursive(root);
    }

    // =====================================================
    // CÁLCULO RECURSIVO DA ALTURA
    // =====================================================
    private int heightRecursive(Node current) {

        // =================================================
        // ÁRVORE VAZIA
        // =================================================
        if (current == null) {

            return -1;
        }

        int leftHeight =
                heightRecursive(current.left);

        int rightHeight =
                heightRecursive(current.right);

        return Math.max(
                leftHeight,
                rightHeight
        ) + 1;
    }

    // =====================================================
    // PERCURSO EM ORDEM SIMÉTRICA
    // =====================================================
    public void inOrder() {

        inOrderRecursive(root);
    }

    private void inOrderRecursive(Node current) {

        if (current != null) {

            inOrderRecursive(current.left);

            System.out.println(
                    current.data.getId()
                    + " - "
                    + current.data.getTitle()
            );

            inOrderRecursive(current.right);
        }
    }

    // =====================================================
    // PERCURSO PRÉ-ORDEM
    // =====================================================
    public void preOrder() {

        preOrderRecursive(root);
    }

    private void preOrderRecursive(Node current) {

        if (current != null) {

            System.out.println(
                    current.data.getId()
                    + " - "
                    + current.data.getTitle()
            );

            preOrderRecursive(current.left);

            preOrderRecursive(current.right);
        }
    }

    // =====================================================
    // PERCURSO PÓS-ORDEM
    // =====================================================
    public void postOrder() {

        postOrderRecursive(root);
    }

    private void postOrderRecursive(Node current) {

        if (current != null) {

            postOrderRecursive(current.left);

            postOrderRecursive(current.right);

            System.out.println(
                    current.data.getId()
                    + " - "
                    + current.data.getTitle()
            );
        }
    }

    // =====================================================
    // PERCURSO EM LARGURA
    // =====================================================
    public void breadthFirst() {

        if (root == null) {

            return;
        }

        Queue<Node> queue =
                new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            System.out.println(
                    current.data.getId()
                    + " - "
                    + current.data.getTitle()
            );

            if (current.left != null) {

                queue.add(current.left);
            }

            if (current.right != null) {

                queue.add(current.right);
            }
        }
    }
}