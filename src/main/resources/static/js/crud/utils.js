/**
 *
 * @param {dates getted from database} data
 * @param {where to inject select} selectName
 */
function addInSelectClient(data, selectName) {
  let selector = document.getElementById(selectName);

  for (let i = 0; i < data.length; i++) {
    let opcion = `${data[i].idClient} - ${data[i].name}`;
    selector.options[i] = new Option(opcion);
  }
}

/**
 *
 * @param {dates getted from database} data
 * @param {where to inject select} selectName
 */
function addInSelectCar(data, selectName) {
  let selector = document.getElementById(selectName);

  for (let i = 0; i < data.length; i++) {
    let opcion = `${data[i].idCar} - ${data[i].name}`;
    selector.options[i] = new Option(opcion);
  }
}

/**
 *
 * @param {dates getted from database} data
 * @param {where to inject select} selectName
 */
function addInSelectGama(data, selectName) {
  let selector = document.getElementById(selectName);

  for (let i = 0; i < data.length; i++) {
    let opcion = `${data[i].idGama} - ${data[i].name}`;
    selector.options[i] = new Option(opcion);
  }
}

//Crear funcion que reciba un array y verifique que todos sus elementos para los input estén ocupado y no estén vacíos

// Crear una funcion que separe el id del select con el método split()

function getIdOfSelect(select) {
  [idToReturn, nameOfElement] = select.split(" - ");
  return idToReturn;
}

function NoOneFieldNull(array) {
  for (let i = 0; i < array.length; i++) {
    const element = array[i];
    if (element == 0 || element == null) {
      return false;
    }
  }
  return true;
}

// Crear función que busca el elemento por su nombre para concatenarlo dentro de la creación del elemento
