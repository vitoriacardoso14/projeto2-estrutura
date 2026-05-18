import java.util.LinkedList;
import java.util.Queue;

public class BST {

    private Node root;

    public BST() {
        root = null;
    }

    // INSERIR
    public void insert(ProgramaNetFlix program) {

        root = insertRecursive(root, program);
    }

    private Node insertRecursive(Node current, ProgramaNetFlix program) {

        if (current == null) {
            return new Node(program);
        }

        if (program.getId().compareTo(current.data.getId()) < 0) {

            current.left = insertRecursive(current.left, program);

        } else if (program.getId().compareTo(current.data.getId()) > 0) {

            current.right = insertRecursive(current.right, program);
        }

        return current;
    }

    // PERCURSO EM ORDEM
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

    // PRÉ-ORDEM
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

    // PÓS-ORDEM
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

    // EM LARGURA
    public void breadthFirst() {

        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();

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

    // BUSCAR POR ID
    public ProgramaNetFlix search(String id) {

        return searchRecursive(root, id);
    }

    private ProgramaNetFlix searchRecursive(Node current, String id) {

        // não encontrou
        if (current == null) {
            return null;
        }

        // encontrou
        if (id.equals(current.data.getId())) {
            return current.data;
        }

        // esquerda
        if (id.compareTo(current.data.getId()) < 0) {

            return searchRecursive(current.left, id);
        }

        // direita
        return searchRecursive(current.right, id);
    }
    
    // analise 1
    public Node getRoot() {
    return root;
}
}