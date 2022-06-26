function sesionNavbar() {
  let isLogged = false;

  if (!isLogged) {
    //<button type="button" class="btn btn-primary" data-bs-target="#modalRegister"> Iniciar sesión </button>

    let button = `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#crearCuenta">Acceder</button>`;

    let formRegister = ` 

    <div class="mb-3 row">
        <label for="inputNameRegister" class="col-sm-2 col-form-label">Nombre</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputNameRegister" placeholder="Tu nombre">
        </div class="mb-3 row">
    </div>

    <div class="mb-3 row">
        <label for="inputEmailRegister" class="col-sm-2 col-form-label">Correo</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputEmailRegister" placeholder="Correo@mail.com">
        </div class="mb-3 row">
    </div>

    <div class="mb-3 row">
        <label for="inputPasswordRegister" class="col-sm-2 col-form-label">Contraseña</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPasswordRegister" placeHolder="contraseña">
        </div>
    </div>

    <div class="mb-3 row">
        <label for="inputDateRegister" class="col-sm-2 col-form-label">Contraseña</label>
        <div class="col-sm-10">
          <input type="date" class="form-control" id="inputDateRegister">
        </div>
    </div>
    `;

    //       <input size="16" type="date" class="form-control">
    let logIn = `

    <div class="mb-3 row">
        <label for="inputEmailLogin" class="col-sm-2 col-form-label">Correo</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputEmailLogin" placeholder="Correo@mail.com">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="inputPasswordLogin" class="col-sm-2 col-form-label">Contraseña</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPasswordLogin" placeHolder="contraseña">
        </div>
    </div>

  
  `;

    let modal = `<div class="modal fade" id="crearCuenta" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalToggleLabel">Crear cuenta</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
        ${formRegister}
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-success" onclick="createAccount()">Crear cuenta</button>
          <button class="btn btn-primary" data-bs-target="#LoginAccount" data-bs-toggle="modal">Tengo una cuenta</button>
        </div>
      </div>
    </div>
  </div>

  
  <div class="modal fade" id="LoginAccount" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalToggleLabel2">Inicar Sesión</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
        ${logIn}
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-warning" data-bs-target="#crearCuenta" data-bs-toggle="modal">Crear cuenta</button>
        <button type="button" class="btn btn-success" onclick="logginAccount()" >Iniciar sesión</button>
        <button type="button" class="btn btn-danger" data-bs-target="#" data-bs-toggle="modal">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
        
    `;

    let buttonAndForm = `${button}${modal}`;

    document.getElementById("navbar-text-to-input-sesion").innerHTML =
      buttonAndForm;
  } else if (isLogged) {
    let button = `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Mi cuenta</button>`;

    let MyAccount = `
    CONTENIDO DE MI CUENTA
    `;

    let modal = ` <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Mi cuenta</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
       ${MyAccount}
        </div>
        <div class="modal-footer">
        <p>Lugar para colocar botones</p>
        </div>
      </div>
    </div>
  </div>`;

    let buttonAndForm = `${button}${modal}`;

    document.getElementById("navbar-text-to-input-sesion").innerHTML =
      buttonAndForm;
  }
}

function createAccount() {
  let actualYear = new Date().getFullYear();

  let name = document.getElementById("inputNameRegister").value;
  let email = document.getElementById("inputEmailRegister").value;
  let password = document.getElementById("inputPasswordRegister").value;
  let birthday = document.getElementById("inputDateRegister").value;
  const [year, day, month] = birthday.split("-");
  let age = actualYear - year;

  function isEmptySomeInput() {
    if (
      name.length == 0 ||
      email.length == 0 ||
      password.length == 0 ||
      birthday.length == 0
    ) {
      return true;
    }
    return false;
  }

  if (!isEmptySomeInput()) {
    let data = {
      email: email,
      password: password,
      name: name,
      age: age,
    };
    console.log(age);

    fetch("http://localhost:8080/api/Client/save", {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then(function (response) {
        if (response.status == 201) {
          alert("Tu cuenta fue creada!");
        }
      })
      .catch(function (error) {
        console.log("Problema al crear el cliente: " + error);
      });
  } else if (isEmptySomeInput()) {
    alert("Hay campos vacíos!");
  }
}

function logginAccount() {
  let email = document.getElementById("inputEmailLogin").value;
  let password = document.getElementById("inputPasswordLogin").value;

  function noOneNull() {
    if (email.length == 0 || password.length == 0) {
      return false;
    }
    return true;
  }

  if (noOneNull()) {
    alert("Ningún elemento está vacío! :D");
  } else {
    alert("Un elemento está vacío :0");
  }
}

//Función que reciba un array y verifique que todos los elementos no son vacíos, reutilizar esta función para la creación de cuenta e inicio de sesión
