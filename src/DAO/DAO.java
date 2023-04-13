package DAO;

public interface DAO<T> {
    /**
     * Permet de récupérer un objet dans la BDD via son Id
     *
     * @param id L'id est l'identification unique de l'objet
     * @return T
     * La fonction retourne un objet crée depuis la BDD
     */
    T find(int id);

    /**
     * Permet d'envoyer les données de l'objet dans la BDD
     *
     * @param obj L'object est ce qu'on veut envoyer dans la BDD
     */
    T create(T object);

    /**
     * Permet de mettre à jour les données de l'objet dans BDD
     *
     * @param obj obj est l'objet que l'on veut mettre à jour dans la BDD
     */
    void update(T obj);

    /**
     * Permet la suppression d'un objet dans la BDD
     *
     * @param id L'id est l'identification unique de l'objet
     */
    void delete(int id);
}
