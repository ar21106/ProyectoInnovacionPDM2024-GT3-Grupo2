<?php
require_once("Conexion.php");

class RecetasModel extends Conexion{
    private $conexion;

    public function __construct(){
        $this->conexion = new Conexion();
        $this->conexion = $this->conexion->getConexion();
    }

    public function insertReceta($nombre, $descripcion, $esFavorito){

        try{
            $sql = "INSERT INTO recetas(nombre,descripcion,esFavorito) VALUES(?,?,?)";
            $parametros = array($nombre, $descripcion, $esFavorito);
            $insert = $this->conexion->prepare($sql);
            $resultado = $insert->execute($parametros);
            return $resultado;

        }catch(Exception $e){
            $resultado = 0;
            return $resultado;
        }
    }
}