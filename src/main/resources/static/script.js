function getEntidades() {
    fetch('/api/v1/realentitys', {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            const lista = document.getElementById('entidades-lista');
            lista.innerHTML = '';

            data.forEach(realEntity => {
                const item = document.createElement('li');

                item.innerHTML = `
                <strong>ID:</strong> ${realEntity.id} <br>
                <strong>Dirección:</strong> ${realEntity.address} <br>
                <strong>Precio:</strong> ${realEntity.price} <br>
                <strong>Tamaño:</strong> ${realEntity.size} m² <br>
                <strong>Descripción:</strong> ${realEntity.description}
            `;

                lista.appendChild(item);
            });
        })
        .catch(error => console.error('Error obteniendo entidades:', error));
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
        .then(response => response.json())
        .then(data => {
            Swal.fire({
                title: 'Creada',
                text: 'Entidad creada con éxito',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });
        })
        .catch(error => console.error('Error creando entidad:', error));
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

