function sesionNavbar() {
  let isRegistered = true;

  if (isRegistered) {
    //<button type="button" class="btn btn-primary" data-bs-target="#modalRegister"> Iniciar sesión </button>

    let logIn = `
  <div class="mb-3 row">
      <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
      <div class="col-sm-10">
      <input type="text" class="form-control" id="staticEmail" placeholder="Correo@mail.com" >
  </div>
  </div>
      <div class="mb-3 row">
          <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
          <div class="col-sm-10">
          <input type="password" class="form-control" id="inputPassword" placeHolder="contraseña">
      </div>
  </div>`;

    let button = `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#crearCuenta">Acceder</button>`;

    let formRegister = ` 
    <div>
        <label  class="col-form-label" for="name">Name</label>
        <div>
        <input type="text" class="form-control" id="name" placeholder="Tu nombre" >
        </div>
    
    </div>
    <div class="mb-3 row">
        <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
        <div class="col-sm-10">
        <input type="text" class="form-control" id="staticEmail" placeholder="Correo@mail.com" >
        </div>
    </div>
        <div class="mb-3 row">
        <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
        <div class="col-sm-10">
        <input type="password" class="form-control" id="inputPassword" placeHolder="contraseña">
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
        <button type="button" class="btn btn-secondary">Crear cuenta</button>
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
        <button type="button" class="btn btn-success" data-bs-target="#crearCuenta" data-bs-toggle="modal">Iniciar sesión</button>
        <button type="button" class="btn btn-danger" data-bs-target="#" data-bs-toggle="modal">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
    
    
    `;

    //   {
    //     <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="Acceder" aria-hidden="true">
    //     <div class="modal-dialog">
    //       <div class="modal-content">
    //         <div class="modal-header">
    //           <h5 class="modal-title" id="exampleModalLabel">Contraseña</h5>
    //           <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
    //         </div>
    //         <div class="modal-body">
    //           ${formRegister}
    //         </div>
    //         <div class="modal-footer">
    //           <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
    //           <button type="button" class="btn btn-primary">Crear cuenta</button>
    //         </div>
    //       </div>
    //     </div>
    //   </div>}

    let buttonAndForm = `${button}${modal}`;

    document.getElementById("navbar-text-to-input-sesion").innerHTML =
      buttonAndForm;
  } else if (!isRegistered) {
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
