/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showMessage()
{
   alert("cualquier cosa");	
}

/*
$(document).ready(function () {
    //funcion que llamamos cuando seleccionamos una tactica
    var storageRef = firebase.storage().ref();
    //var imagenesFBRef = firebase.database().ref().child("imagenes");
    fichero = document.getElementById("dirArchivo");
    fichero.addEventListener("change",subirArchivoFirebase,false);
    
    alert("entró js");
    
    function subirArchivoFirebase()
    {
        var archivoSubir = fichero.files[0];
        var ruta = document.getElementById("txtRuta").value;
        var uploadTask = storageRef.child("archivos/"+ruta+"/"+archivoSubir.name).put(archivoSubir);
        
        uploadTask.on('state_changed', function(snapshot){
          var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
          console.log('Upload is ' + progress + '% done');
          switch (snapshot.state) {
            case firebase.storage.TaskState.PAUSED: // or 'paused'
              console.log('Upload is paused');
              break;
            case firebase.storage.TaskState.RUNNING: // or 'running'
              console.log('Upload is running');
              break;
          }
        }, function(error) {
          // Handle unsuccessful uploads
        }, function() {
          var downloadURL = uploadTask.snapshot.downloadURL;
          //poner la url en el campo del formulario de la imagen para que se guarde en la bd
        });
    }


});

*/