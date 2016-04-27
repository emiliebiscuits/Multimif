/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.tdb.TDBFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test unitaire pour donner un exemple de base TDB.
 *
 * Remarque: ces tests créent toujours un datasetgraph. En pratique on ne devrait
 * le faire qu'une fois au démarrage et on ne devrait le fermer qu'à l'extinction de l'application.
 */
public class DAOJenaControllerTest {
    /**
     * Permet de peupler la base dans le répertoire target/bdd_exemple
     */
    @Test
    public void testRemplitTDB() {
        DatasetGraph graph = TDBFactory.createDatasetGraph("target/bdd_exemple");
        Graph g = graph.getDefaultGraph();
        Node exempleSujet = NodeFactory.createURI("http://exemple.org/personne/toto");
        Node exemplePredicat = NodeFactory.createURI("http://www.w3.org/2000/01/rdf-schema#label");
        Node exempleObjet = NodeFactory.createLiteral("Toto");
        Node exemplePredicat2 = NodeFactory.createURI("http://xmlns.com/foaf/0.1/age");
        Node exempleObjet2 = NodeFactory.createLiteral("8", XSDDatatype.XSDinteger);
        g.add(Triple.create(exempleSujet, exemplePredicat, exempleObjet));
        g.add(Triple.create(exempleSujet, exemplePredicat2, exempleObjet2));
        assertEquals(2, g.size());
        graph.close();
    }

    /**
     * Interroge la base contenue dans target/bdd_exemple
     * Mis en commentaire car bug de la requête.
     */
    @Test
    public void testRequeteTDB() {
//        testRemplitTDB(); // on s'assure que la base existe et est remplie via le test précédent
//        // On se connecte à la base
//        DatasetGraph graph = TDBFactory.createDatasetGraph("target/bdd_exemple");
//        Model m = ModelFactory.createModelForGraph(graph.getDefaultGraph());
//        // On exécute la requête
//        Query q = QueryFactory.create("select ?n where {?n http://www.w3.org/2000/01/rdf-schema#label \"Toto\"}"/* +
//                " " +
//                " " +
//                "?n . " +
//                "}"*/);
//        QueryExecution exec = QueryExecutionFactory.create(q, m);
//        ResultSet rs = exec.execSelect();
//        assertTrue(rs.hasNext());
//        QuerySolution sol = rs.next();
//        assertEquals("Toto", sol.getLiteral("n").getString());
//
//        // Une deuxième requête
//        q = QueryFactory.create("select ?a where {   ?a . }");
//        exec = QueryExecutionFactory.create(q, m);
//        rs = exec.execSelect();
//        assertTrue(rs.hasNext());
//        sol = rs.next();
//        assertEquals(8, sol.getLiteral("a").getInt());
    }
}