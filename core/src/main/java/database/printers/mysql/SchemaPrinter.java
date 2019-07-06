package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import structures.TreeNode;

import java.util.Map;

/**
 * Class for formed DDL for DATABASE
 */
@Printer(typeMeta = TypeMeta.DATABASE)
public class SchemaPrinter implements Printers {

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance){

        String ddlSchema = "";
        StringBuilder sb = new StringBuilder();
        sb.append("DROP SCHEMA IF EXISTS ").append(node.getNameElement()).append(";");
        sb.append(System.lineSeparator());
        sb.append("CREATE SCHEMA ").append(node.getNameElement()).append(";");
        ddlSchema = sb.toString();

        return ddlSchema;
    }
}
