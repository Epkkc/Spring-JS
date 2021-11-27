function fillTable(data, tableId) {
    const tbody = document.querySelector(`.${tableId}_tbody`)
    let index = 0
    for (let user of data) {
        let row = document.createElement('tr')

        const roles = user['roles']
        const rolesString = convertRolesToString(roles)
        row.innerHTML = `
            <td>${user['user_id']}</td>
            <td>${user['name']}</td>
            <td>${user['lastname']}</td>
            <td>${user['yearOfBirth']}</td>
            <td>${user['username']}</td>
            <td>${rolesString.length === 0 ? 'NO ROLES' : rolesString}</td>
        `

        let tdEdit = document.createElement('td')

        let editButton = document.createElement('button')
        editButton.classList.add("btn", "btn-info")
        editButton.setAttribute("data-toggle", "modal")
        editButton.setAttribute("data-target", "#patch_modal")
        editButton.textContent = "Edit"
        editButton.id = `edit_button_${index}`
        editButton.addEventListener('click', () => {
            console.log("EditListenerId", `${index}`)
        }, false)

        tdEdit.appendChild(editButton)

        let tdDelete = document.createElement('td')

        let deleteButton = document.createElement('button')
        deleteButton.classList.add("btn", "btn-danger")
        deleteButton.setAttribute("data-toggle", "modal")
        deleteButton.setAttribute("data-target", "#delete_modal")
        deleteButton.textContent = "Delete"
        deleteButton.id = `delete_button_${user['user_id']}`
        deleteButton.addEventListener('click', (event) => {
            console.log("DeleteListenerId", `${index}`)
            console.log("DeleteListenerEvent", event.target.id)
            deleteButtonGetUser(event.target.id)
        }, false)

        tdDelete.appendChild(deleteButton)

        row.appendChild(tdEdit)
        row.appendChild(tdDelete)

        tbody.appendChild(row)

        index++
    }
}

function convertRolesToString(roles) {
    let rolesString = ''
    if (roles.length < 1) {
        rolesString = 'NO ROLES'
    } else if (roles.length === 1) {
        rolesString += roles[0]['roleType']
    } else {
        for (let i = 0; i < roles.length; i++) {
            if (i === roles.length - 1) {
                rolesString += roles[i]['roleType']
            } else {
                rolesString += roles[i]['roleType'] + ' '
            }
        }
    }
    return rolesString
}

function deleteButtonGetUser(id) {
    const numberId = id.split('delete_button_')[1]
    fetch(`http://localhost:8080/api/users/${numberId}`)
        .then(res => {
            return res.json()
        })
        .then(data => {
            let user = data
            console.log('DeleteUser', user)
            deleteButtonFillInputs(user, numberId)
        })
}

function deleteButtonFillInputs(user, id) {
    document.querySelector('#id_input_delete').value = user['id']
    document.querySelector('#first_name_input_delete').value = user['name']
    document.querySelector('#last_name_input_delete').value = user['lastname']
    document.querySelector('#age_input_delete').value = user['yearOfBirth']
    document.querySelector('#email_input_delete').value = user['username']
    let roles = user['roles']
    fillRolesSelectorAdd(roles, 'role_select_delete')

    // Надо добавить EventListener для кнопки delete внутри формы

    let button = document.querySelector('#button_delete')
    button.addEventListener('click', () => deleteButtonRequest(id), false)
}

function deleteButtonRequest(id) {
    fetch(`http://localhost:8080/api/users/${id}`, {
        method: 'DELETE'
    })
    getUsersAndFillTable()
}


function fillHeading(currentUser) {
    const heading = document.querySelector(`.heading_info`)
    let spanEmail = document.createElement('span')
    spanEmail.classList.add('font-weight-bold')
    spanEmail.textContent = `${currentUser['username']}`
    let spanRoles = document.createElement('span')
    spanRoles.textContent = ` with roles: ${convertRolesToString(currentUser['roles'])}`

    heading.appendChild(spanEmail)
    heading.appendChild(spanRoles)
}

function getUsersAndFillTable() {
    fetch("http://localhost:8080/api/users")
        .then(res => {
            return res.json()
        })
        .then(data => {
            let allUsers = data
            console.log('AllUsers', data)
            fillTable(allUsers, "admin_table")
        })
}

function fillHeadingAndUserTable() {
    fetch("http://localhost:8080/api/current-user")
        .then(res => {
            return res.json()
        })
        .then(data => {
            let currentUser = data
            console.log('CurrentUser', currentUser)
            fillHeading(currentUser)
            fillTable([currentUser], "user_table")
        })
}

function addNewUser() {
    const requestBody = {}

    // Заполнение requestBody
    requestBody['name'] = document.querySelector('#first_name_input_add').value
    requestBody['lastname'] = document.querySelector('#last_name_input_add').value
    requestBody['yearOfBirth'] = document.querySelector('#age_input_add').value
    requestBody['username'] = document.querySelector('#email_input_add').value
    requestBody['password'] = document.querySelector('#password_input_add').value

    requestBody['roles'] = []
    let selectElement = document.querySelector('#role_select_add')
    let selectedValues = Array.from(selectElement.selectedOptions)
        .map(option => option.value)
    for (let selectedValue of selectedValues) {
        requestBody['roles'].push({
            'roleType': selectedValue
        })
    }


    console.log('SelectedValues', selectedValues)

    console.log('Request body', requestBody)

    fetch("http://localhost:8080/api/users", {
        method: 'POST',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(requestBody)
    })
        .then(res => res.json())
        .then(data => console.log(data))
}

function fillRolesSelectorAdd(roles, id) {
    const selector = document.querySelector(`#${id}`)
    for (let role of roles) {
        const option = document.createElement('option')
        option.textContent = role['roleType']
        selector.appendChild(option)
    }
}

function getAllRoles() {
    fetch("http://localhost:8080/api/roles")
        .then(res => {
            return res.json()
        })
        .then(data => {
            let allRoles = data
            console.log('AllRoles', allRoles)
            fillRolesSelectorAdd(allRoles, 'role_select_add')
        })
}

getUsersAndFillTable()
fillHeadingAndUserTable()

document.querySelector('#submit_button_add').addEventListener('click', () => {
    addNewUser()
}, false)

getAllRoles()
