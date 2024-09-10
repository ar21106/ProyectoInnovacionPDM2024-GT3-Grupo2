<?php
require_once("./modelo/RecetasModel.php");
header('Content-type: application/json');

class RecetasController{
    private $objRecetasModel;

    public function __construct(){
        $this->objRecetasModel = new RecetasModel();
    }

    //insertar receta
    public function insertReceta($nombre, $descripcion, $esFavorito){
        $resultado = $this->objRecetasModel->insertReceta($nombre, $descripcion, $esFavorito);
        $json = array();
        if ($resultado==true){
            http_response_code(201);
            $json['Estado'] = 'Exito';
            $json['Mensaje'] = "Receta $nombre registrada con exito";
            $json['data'] = [];
            echo json_encode($json);
        }else{
            http_response_code(202);
            $json['Estado'] = 'Error';
            $json['Mensaje'] = "Receta $nombre no pudo ser registrada";
            $json['data'] = [];
            echo json_encode($json);
        }
    }
}
