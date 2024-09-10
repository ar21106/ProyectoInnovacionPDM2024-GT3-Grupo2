<?php
require_once("./controlador/RecetasController.php");

$objRecetasController = new RecetasController();

//echo "Metodo HTTP: ".$_SERVER['REQUEST_METHOD'];

switch($_SERVER['REQUEST_METHOD']){
    case 'POST': //insertar
        /*
        Endpoint: Recetas
        Parameters: nombre, descripcion, esFavorito
        Method: POST
        */
        if(
            isset($_POST['nombre']) and $_POST['nombre'] != null and
            isset($_POST['descripcion']) and $_POST['descripcion'] != null and
            isset($_POST['esFavorito']) and $_POST['esFavorito'] != null){

                $objRecetasController->insertReceta($_POST['nombre'], $_POST['descripcion'], $_POST['esFavorito']);
        }else{
            //error
            http_response_code(202);
            $json['Estado'] = 'Error';
            $json['Mensaje'] = "No se pudo guardar, verifique que todos los campos este llenos";
            $json['data'] = [];
            echo json_encode($json);
        }
    break;

    case 'GET': //obtener
    break;

    case 'PUT': //actualizar
    break;

    case 'DELETE': //borrar
        break;
}
