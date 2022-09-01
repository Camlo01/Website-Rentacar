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
    let button = `<button type="button" class="btn btn-primary navbar--btn" data-bs-toggle="modal" data-bs-target="#myAccountModal">Mi cuenta</button>`;

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
              <td>*****</td> -->
              <!-- <td>${localStorage.getItem("password")}</td> -->
          </tr>
      </tbody>
  </table>

      `;

    let modal = ` <div class="modal fade" id="myAccountModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title tittles-modals-forms" id="exampleModalLabel">Mi cuenta</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
          ${myAccountInfo}
          ${menuOptionsByTypeClient()}
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
  let name = document.getElementById("inputNameRegister").value;
  let email = document.getElementById("inputEmailRegister").value;
  let password = document.getElementById("inputPasswordRegister").value;
  let birthDate = document.getElementById("inputDateRegister").value;

  if (validateEmailAndPassword(email, password) && isAgeValid(birthDate)) {
    let data = {
      email: email,
      password: password,
      name: name,
      birthDate: birthDate,
    };

    fetch(`${URLapi}/client/save`, {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-type": "application/json; charset=UTF-8" },
    })
      .then(function (response) {
        if (response.status == 201) {
          let account = {
            email: email,
            password: password,
            birthDate: birthDate,
          };
          fetch(`${URLapi}/client/login`, {
            method: "POST",
            body: JSON.stringify(account),
            headers: { "Content-type": "application/json; charset=UTF-8" },
          })
            .then((response) => response.json())
            .then((data) => {
              console.log(data);
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

    fetch(`${URLapi}/client/login`, {
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
  location.reload();
}

function welcome() {
  if (isLogged()) {
    document.getElementById("wherePrintName").innerHTML =
      localStorage.getItem("name");
  } else {
    document.getElementById("wherePrintName").innerHTML = "Invitado";
  }
}

function loginLogic(data) {
  if (!(data.idClient == null)) {
    localStorage.setItem("logged", "si");
    localStorage.setItem("idClient", data.idClient);
    localStorage.setItem("name", data.name);
    localStorage.setItem("email", data.email);
    localStorage.setItem("password", data.password);
    localStorage.setItem("KeyClient", data.keyClient);
    localStorage.setItem("ClientType", data.type);

    sesionNavbar();
    welcome();
    location.reload();
  } else {
    alert("Hubo un problema: " + data.email);
  }
}

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

function isAgeValid(birthDate) {
  let cumpleanos = new Date(birthDate); //.toISOString();
  let horaActual = new Date(); //.toISOString();

  let miliSegundosDia = 24 * 60 * 60 * 1000;

  let milisegundosTranscurridos = Math.abs(
    cumpleanos.getTime() - horaActual.getTime()
  );

  let diasTranscurridos = Math.round(
    milisegundosTranscurridos / miliSegundosDia
  );

  // console.log("La hora local es " + horaActual.toDateString());
  // console.log("El nacimiento es " + cumpleanos.toDateString());
  // console.log(diasTranscurridos + " vs 6575");
  if (diasTranscurridos >= 6575) {
    // console.log("Tiene 18 años");
    return true;
  } else {
    alert("Lo sentimos, si eres menor de edad no puedes craer tu cuenta :(");
    // console.log("Es menor de 18 años");
    return false;
  }
}

function validateEmailAndPassword(email, password) {
  let emailRule = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
  let passwordRule =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,15}$/;

  if (emailRule.test(email)) {
    if (passwordRule.test(password)) {
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

function menuOptionsByTypeClient() {
  const typeClient = localStorage.getItem("ClientType");

  let toReturn = `
  <h2>¿Qué deseas hacer?<h2>
  <br>
  `;

  //Buttons to redirect
  const buttonToProfile = `
  <p>En tu perfíl podrás administrar tus reservas</p>
  <a class="btn btn-primary" href="./client/clientPage.html" role="button">Ver Mi Perfil</a>
  `;

  const buttonToAdministrate = `
  <hr>
  <p>Tu zona de trabajo:</p>
  <a class="btn btn-info" href="./admin/adminPage.html" role="button">Administrar</a>

  `;

  //Content for every type of user

  const menuForClient = `
   ${buttonToProfile}
  <br>
  `;

  const menuForSupport = `
  <h2>¡Bienvenido miembro del equipo de soporte!</h2>
  <br>
  ${buttonToProfile}
  ${buttonToAdministrate}
  `;

  const menuForAdmin = `
  <h2>Bienvenido usuario <strong>administrador</strong></h2>
  <br>
  ${buttonToProfile}
  ${buttonToAdministrate}
    `;
  const menuForDeveloper = `
  <h2>Welcome to your profile <strong>developer</strong> user</h2>
  <br>
  ${buttonToProfile}
  ${buttonToAdministrate}
  
  `;

  //return content depending on the type of the user registered
  switch (typeClient) {
    case "CLIENT":
      toReturn += menuForClient;
      break;
    case "SUPPORT":
      toReturn += menuForSupport;
      break;
    case "ADMIN":
      toReturn += menuForAdmin;
      break;
    case "DEVELOPER":
      toReturn += menuForDeveloper;
      break;
  }
  return toReturn;
}
