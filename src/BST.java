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
    // OPÇÃO 3 DO EDITAL - INSERIR PROGRAMA
    // =====================================================
    public void insert(ProgramaNetFlix program) {

        root = insertRecursive(root, program);
    }

    // =====================================================
    // INSERÇÃO RECURSIVA (SUPORTE OPÇÃO 1 E 3)
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
    // OPÇÃO 4 DO EDITAL - BUSCAR PROGRAMA COM MÉTRICAS
    // =====================================================
    public void searchWithMetrics(String id) {

        // INICIA O MONITOR DE TEMPO (Exigência do edital)
        long startTime = System.nanoTime();

        // ZERA AS COMPARAÇÕES
        comparisons = 0;

        Node result = searchRecursiveMetrics(root, id);

        // PARA O MONITOR DE TEMPO
        long endTime = System.nanoTime();

        System.out.println("\n=== RESULTADO DA BUSCA ===");

        // =================================================
        // EXIBE OS DADOS SE ENCONTRADO
        // =================================================
        if (result != null) {

            System.out.println(
                    "Programa encontrado: "
                    + result.data.getTitle()
            );

            System.out.println(
                    "Tipo: "
                    + result.data.getShowType()
                    + " | Ano: "
                    + result.data.getReleaseYear()
            );

            System.out.println(
                    "Nota IMDB: "
                    + result.data.getImdbScore()
            );

        } else {

            System.out.println(
                    "Programa com ID '"
                    + id
                    + "' não foi encontrado."
            );
        }

        // =================================================
        // EXIBE AS MÉTRICAS EXIGIDAS
        // =================================================
        System.out.println(
                "Comparações realizadas: "
                + comparisons
        );

        System.out.println(
                "Tempo de execução: "
                + (endTime - startTime)
                + " ns"
        );
    }

    // =====================================================
    // BUSCA RECURSIVA COM MÉTRICAS
    // =====================================================
    private Node searchRecursiveMetrics(
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

            return current;
        }

        // =================================================
        // PROCURA À ESQUERDA
        // =================================================
        if (compare < 0) {

            return searchRecursiveMetrics(
                    current.left,
                    id
            );
        }

        // =================================================
        // PROCURA À DIREITA
        // =================================================
        return searchRecursiveMetrics(
                current.right,
                id
        );
    }

    // =====================================================
    // OPÇÃO 5 DO EDITAL - REMOVER PROGRAMA
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
            
            System.out.println(
                    "Erro: ID '"
                    + id
                    + "' não encontrado para remoção."
            );

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

            System.out.println(
                    "Programa '"
                    + current.data.getTitle()
                    + "' removido com sucesso!"
            );

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
    // PROCURA O MENOR NÓ (SUPORTE PARA REMOÇÃO)
    // =====================================================
    private Node findSmallest(Node current) {

        while (current.left != null) {

            current = current.left;
        }

        return current;
    }

    // =====================================================
    // OPÇÃO 6 DO EDITAL - EXIBIR A ALTURA DA ÁRVORE
    // =====================================================
    public int height() {

        return heightRecursive(root);
    }

    // =====================================================
    // CÁLCULO RECURSIVO DA ALTURA
    // =====================================================
    private int heightRecursive(Node current) {

        // =================================================
        // ÁRVORE VAZIA OU NÓ FOLHA
        // =================================================
        if (current == null) {

            return -1; // -1 para que folhas tenham altura 0
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
    // SUPORTE PARA A OPÇÃO 2 DO EDITAL - ANÁLISES DE DADOS
    // (Percurso em Ordem Simétrica)
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
    // SUPORTE PARA A OPÇÃO 2 DO EDITAL - ANÁLISES DE DADOS
    // (Percurso em Pré-Ordem)
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
    // SUPORTE PARA A OPÇÃO 2 DO EDITAL - ANÁLISES DE DADOS
    // (Percurso em Pós-Ordem)
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
    // SUPORTE PARA A OPÇÃO 2 DO EDITAL - ANÁLISES DE DADOS
    // (Percurso em Largura)
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