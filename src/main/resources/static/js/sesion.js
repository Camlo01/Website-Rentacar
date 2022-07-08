/**
 * Valores de LocalStorage
 *
 * "logged"   |   "si"
 * "email"    |   "email getted" / "null"
 *
 */

function isLogged() {
  if (localStorage.getItem("logged") == "si") {
    return true;
  } else {
    return false;
  }
}

/**
 * Se inserta ---------------------->
 *
 * SÍ está registrado: -------->
 * - botón "Mi cuenta"
 * - LET Formulario de crear cuenta
 * - LET form de login
 *
 * - variable "modal" que contiene ambos modales con los formularios insertados
 *
 * NO estár registrado: -------->
 *
 * - boton "Acceder"
 * - LET myAccountInfo = tabla con la información de la cuenta
 * - modal que incluye myAccountInfo
 *
 * -------->
 *
 * y finalmente se inserta en el div con el id = "navbar-text-to-input-sesion"
 *
 */
function sesionNavbar() {
  if (!isLogged()) {
    let button = `<button type="button" class="btn btn-primary navbar--btn" data-bs-toggle="modal" data-bs-target="#crearCuenta">Acceder</button>`;
    let formRegister = ` 

      <div class="mb-3 row div-label-input-form">
          <label for="inputNameRegister" class="col-sm-2 col-form-label label--form">Nombre:</label>
          <div class="col-6">
              <input type="text" class="form-control input--modal" id="inputNameRegister" placeholder="Tu nombre">
          </div class="mb-3 row">
      </div>

      <div class="mb-3 row div-label-input-form">
          <label for="inputEmailRegister" class="col-sm-2 col-form-label label--form">Correo: </label>
          <div class="col-6">
              <input type="text" class="form-control input--modal" id="inputEmailRegister" placeholder="Correo@mail.com">
          </div class="mb-3 row">
      </div>

      <div class="mb-3 row div-label-input-form">
          <label for="inputPasswordRegister" class="col-sm-2 col-form-label label--form">Contraseña: </label>
          <div class="col-6">
              <input type="password" class="form-control input--modal" id="inputPasswordRegister" placeHolder="contraseña">
          </div>
      </div>

      <div class="mb-3 row div-label-input-form">
          <label for="inputDateRegister" class="col-sm-2 col-form-label label--form">Nacimiento: </label>
          <div class="col-6">
            <input type="date" class="form-control input--modal" id="inputDateRegister">
          </div>
      </div>
      `;

    //       <input size="16" type="date" class="form-control">
    let logIn = `

      <div class="mb-3 row div-label-input-form">
          <label for="inputEmailLogin" class="col-sm-2 col-form-label label--form">Correo: </label>
          <div class="col-6">
              <input type="text" class="form-control input--modal" id="inputEmailLogin" placeholder="Correo@mail.com">
          </div>
      </div>
      <div class="mb-3 row div-label-input-form">
          <label for="inputPasswordLogin" class="col-sm-2 col-form-label label--form">Contraseña: </label>
          <div class="col-6">
              <input type="password" class="form-control input--modal" id="inputPasswordLogin" placeHolder="contraseña">
          </div>
      </div>

    
    `;

    let modal = `<div class="modal fade" id="crearCuenta" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title tittles-modals-forms" id="exampleModalToggleLabel">Crear cuenta</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
          ${formRegister}
          </div>
          <div class="modal-footer">
          <button type="button" id="crearCuentaButton" class="btn btn-success botones-navbars" data-bs-target="#" data-bs-toggle="modal" onclick="createAccount()">Crear cuenta</button>
            <button class="btn btn-primary botones-navbars" data-bs-target="#LoginAccount" data-bs-toggle="modal">Tengo una cuenta</button>
          </div>
        </div>
      </div>
    </div>

    
    <div class="modal fade" id="LoginAccount" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title tittles-modals-forms" id="exampleModalToggleLabel2">Inicar Sesión</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
          ${logIn}
          </div>
          <div class="modal-footer">
          <button type="button" class="btn btn-warning botones-navbars" data-bs-target="#crearCuenta" data-bs-toggle="modal">Crear cuenta</button>
          <button type="button" class="btn btn-success botones-navbars"  data-bs-target="#" data-bs-toggle="modal" onclick="logginAccount()" >Iniciar sesión</button>
          <button type="button" class="btn btn-danger botones-navbars"  data-bs-target="#" data-bs-toggle="modal">Cancelar</button>
          </div>
        </div>
      </div>
    </div>
          
      `;

    let buttonAndForm = `${button}${modal}`;

    document.getElementById("navbar-text-to-input-sesion").innerHTML =
      buttonAndForm;
  } else if (isLogged) {
    let button = `<button type="button" class="btn btn-primary navbar--btn" data-bs-toggle="modal" data-bs-target="#exampleModal">Mi cuenta</button>`;

    let myAccountInfo = `
      <table class="table">
      <thead>
          <tr>
              <th scope="col">Nombre</th>
              <th scope="col">Correo</th>
              <th scope="col">Contraseña</th>
          </tr>
      </thead>
      <tbody>
          <tr>
              <td>${localStorage.getItem("name")}</td>
              <td>${localStorage.getItem("email")}</td>
              <td>${localStorage.getItem("password")}</td>
          </tr>
      </tbody>
  </table>
      `;

    let modal = ` <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title tittles-modals-forms" id="exampleModalLabel">Mi cuenta</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
          ${myAccountInfo}
          </div>
          <div class="modal-footer">
          <button type="button" class="btn btn-danger botones-navbars"  data-bs-target="#" data-bs-toggle="modal" onclick="logOut()" >Cerrar sesión</button>
          </div>
        </div>
      </div>
    </div>`;

    let buttonAndForm = `${button}${modal}`;

    document.getElementById("navbar-text-to-input-sesion").innerHTML =
      buttonAndForm;
  }
}

/**
 * Logica para crear cuenta ---------------------->
 *
 * - Toma los valores de los distintos inputs
 * - Se verifia que todos los campos se hayan llenado
 *
 * SÍ están completos todos los campos: ----->
 *
 * - se hace una petición POST
 *
 * NO están completos los campos: ----------->
 *
 * se lanza una alerta diciendo que hay campos vacíos
 *
 */
function createAccount() {
  let actualYear = new Date().getFullYear();

  let name = document.getElementById("inputNameRegister").value;
  let email = document.getElementById("inputEmailRegister").value;
  let password = document.getElementById("inputPasswordRegister").value;
  let birthDate = document.getElementById("inputDateRegister").value;

  if (validateEmailAndPassword(email, password)) {
    console.log("Entra");
    let data = {
      email: email,
      password: password,
      name: name,
      birthDate: birthDate,
    };

    fetch("http://localhost:8080/api/Client/save", {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then(function (response) {
        if (response.status == 201) {
          alert("Tu cuenta fue creada!");
          let account = {
            email: email,
            password: password,
          };
          fetch(`http://localhost:8080/api/Client/login`, {
            method: "POST",
            body: JSON.stringify(account),
            headers: { "Content-type": "application/json; charset=UTF-8" },
          })
            .then((response) => response.json())
            .then((data) => {
              loginLogic(data);
            });
        }
      })
      .catch(function (error) {
        console.log("Problema al crear el cliente: " + error);
      });
    clearInputs();
  } else if (isEmptySomeInput()) {
    alert("Hay campos vacíos!");
  }
}

/**
 *  función que hace el inicio de sesión la cual para por la url el correo y la contraseña,
 * en caso de coincidir retornará el objeto con la información completa, y si no, devolverá un objeto vacío
 *
 */
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
    let data = {
      email: email,
      password: password,
    };

    fetch(`http://localhost:8080/api/Client/login`, {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then((Response) => Response.json())
      .then((data) => {
        loginLogic(data);
      });
    // .catch((error) => alert("El problema obtenido fue: " + error));
  } else {
    alert("Un elemento está vacío :0");
  }

  clearInputs();
}

/**
 * Función que borra todo todo el localStorage
 */
function logOut() {
  localStorage.clear();
  sesionNavbar();
  welcome();
  // localStorage.removeItem()
}

function welcome() {
  if (isLogged()) {
    let nombre = (document.getElementById("wherePrintName").innerHTML =
      localStorage.getItem("name"));
  } else {
    let nombre = (document.getElementById("wherePrintName").innerHTML =
      "Invitado");
  }
  inputNameRegister;
}

function loginLogic(data) {
  if (!(data.idClient == null)) {
    localStorage.setItem("logged", "si");
    localStorage.setItem("name", data.name);
    localStorage.setItem("email", data.email);
    localStorage.setItem("password", data.password);
    sesionNavbar();
    welcome();
  } else {
    alert(data.email);
  }
}

/**
 * ----- UTILITIES ---------------------
 */

/**
 * Clear inputs before press a button
 */
function clearInputs() {
  document.getElementById("inputNameRegister").value = null;
  document.getElementById("inputEmailRegister").value = null;
  document.getElementById("inputPasswordRegister").value = null;
  document.getElementById("inputDateRegister").value = null;
  document.getElementById("inputEmailLogin").value = null;
  document.getElementById("inputPasswordLogin").value = null;
}

function validateEmailAndPassword(email, password) {
  let emailRule = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
  let passwordRule = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])\w{8,}$/;

  if (emailRule.test(email)) {
    console.log("El correo fue validado correctamente");
    if (passwordRule.test(password)) {
      console.log("Contraseña fue validada correctamente");
      return true;
    } else {
      alert(
        "La contraseña es insegura, intenta agregando letras y números y que seá más de 8 carácteres sin colocar espacios"
      );
    }
  } else {
    alert("Escribe un correo válido");
  }
  return false;
}
