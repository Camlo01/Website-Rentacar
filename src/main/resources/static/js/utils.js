function addInSelect(data, selectName) {
  let selector = document.getElementById(selectName);
  for (let i = 0; i < data.length; i++) {
    selector.options[i] = new Option(data[i].name);
  }
}