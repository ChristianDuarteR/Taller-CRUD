let currentPage = 0;
let totalPages = 0;
function getEntities(page) {
    currentPage = page;
    const addressFilter = document.getElementById('filterAddress').value;
    const priceFilter = document.getElementById('filterPrice').value;
    const sizeFilter = document.getElementById('filterSize').value;
    const minPrice = priceFilter ? priceFilter : 0;
    const maxPrice = priceFilter ? priceFilter : 1000000;
    const minSize = sizeFilter ? sizeFilter : 0;
    const maxSize = sizeFilter ? sizeFilter : 1000;

    const url = `/api/v1/realentitys?page=${currentPage}&address=${addressFilter}&minPrice=${minPrice}&maxPrice=${maxPrice}&minSize=${minSize}&maxSize=${maxSize}`;


    fetch(url, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            totalPages = data.totalPages;
            renderEntities(data.content);
            updatePagination();
        })
        .catch(error => console.error('Error obteniendo entidades:', error));
}

function renderEntities(entidades) {
    const lista = document.getElementById('entidades-lista');
    lista.innerHTML = '';

    if (entidades.length === 0) {
        lista.innerHTML = '<li>No se encontraron entidades.</li>';
        return;
    }

    entidades.forEach(entidad => {
        const li = document.createElement('li');
        li.classList.add('entidad-card');
        li.innerHTML = `
            <div class="entidad-header">${entidad.address}</div>
            <div class="entidad-info"><span>Precio:</span> ${entidad.price} COP</div>
            <div class="entidad-info"><span>Tamaño:</span> ${entidad.size} m²</div>
            <div class="entidad-info"><span>Descripción:</span> ${entidad.description}</div>
            </div>
        `;
        lista.appendChild(li);
    });
}

function updatePagination() {
    const pageInfo = document.getElementById('pageInfo');
    pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages}`;

    document.getElementById('prevPage').disabled = currentPage === 0;
    document.getElementById('nextPage').disabled = currentPage === totalPages - 1;
}

function changePage(newPage) {
    console.log(newPage);
    if (newPage < 0 || newPage >= totalPages) return;
    getEntities(newPage);
}

function applyFilters() {
    getEntities(0);
}



function validateCreate() {
    const address = document.getElementById('address').value.trim();
    const price = parseFloat(document.getElementById('price').value);
    const size = parseFloat(document.getElementById('size').value);
    const description = document.getElementById('description').value.trim();

    if (!address || !description || isNaN(price) || price <= 0 || isNaN(size) || size <= 0) {
        Swal.fire({
            title: 'Advertencia',
            text: 'Porfavor verifique los campos e intente nuevamente',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    createEntity(address, price, size, description);
}

function validateUpdate() {
    const id = parseInt(document.getElementById('idUpdate').value);
    const address = document.getElementById('addressUpdate').value.trim();
    const price = parseFloat(document.getElementById('priceUpdate').value);
    const size = parseFloat(document.getElementById('sizeUpdate').value);
    const description = document.getElementById('descriptionUpdate').value.trim();

    if (isNaN(id) || id <= 0 || !address || !description || isNaN(price) || price <= 0 || isNaN(size) || size <= 0) {
        Swal.fire({
            title: 'Advertencia',
            text: 'Porfavor verifique los campos e intente nuevamente',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    updateEntity(id, address, price, size, description);
}


function createEntity() {
    const address = document.getElementById('address').value;
    const price = document.getElementById('price').value;
    const size = document.getElementById('size').value;
    const description = document.getElementById('description').value;


    fetch('/api/v1/realentitys', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            address: address,
            price: price,
            size: size,
            description: description
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                Swal.fire({
                    title: 'Error',
                    text: 'Entidad NO pudo ser creada',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });            }
        })
        .then(data => {
            Swal.fire({
                title: 'Creada',
                text: 'Entidad creada con éxito',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Hubo un problema al crear la entidad',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
            console.error('Error creando entidad:', error);
        });
}

function updateEntity() {
    const id = document.getElementById('idUpdate').value;
    const address = document.getElementById('addressUpdate').value;
    const price = document.getElementById('priceUpdate').value;
    const size = document.getElementById('sizeUpdate').value;
    const description = document.getElementById('descriptionUpdate').value;
    fetch(`/api/v1/realentitys/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            address: address,
            price: price,
            size: size,
            description: description
        })
    })
        .then(response => response.json())
        .then(data => {
            Swal.fire({
                title: 'Actualizado',
                text: 'Entidad actualizada con éxito',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });
        })
        .catch(error => console.error('Error actualizando entidad:', error));
}

function deleteEntity() {
    event.preventDefault();

    const id = document.getElementById('id').value;
    fetch(`/api/v1/realentitys/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                Swal.fire({
                    title: 'Eliminado',
                    text: 'Entidad eliminada con éxito',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                });

            } else {
                    Swal.fire({
                        title: 'Error',
                        text: 'Error eliminando la entidad. Por favor,verifica el ID e intenta nuevamente.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
            }
        })
        .catch(error => console.error('Error eliminando entidad:', error));
}