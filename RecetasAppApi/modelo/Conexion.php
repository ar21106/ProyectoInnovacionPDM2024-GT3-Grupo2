<?php

class Conexion{
    private $host = "localhost";
    private $user = "root";
    private $password = "";
    private $db = "recetasappdb";
    private $conDb;

    public function __construct(){
        $conectionString = "mysql:host=".$this->host.";dbname=".$this->db.";charset=utf8";

        try{
            $this->conDb = new PDO($conectionString, $this->user, $this->password);
            $this->conDb->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        }catch(Exception $e){
            $this->conDb = "Error de conexion";
            http_response_code(404);
            $json = array();
            $json["Estado"] = "error";
            $json["Mensaje"] = $e->getMessage();
            $json["data"] = [];
            echo json_encode($json);
            exit;
        }
    }

    public function getConexion(){
        return $this->conDb;
    }
}