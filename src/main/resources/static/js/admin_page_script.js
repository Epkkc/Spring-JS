let editButtonEventListener
let deleteButtonEventListener

function fillTable(data, tableId) {
    clearTable(tableId)
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

        if (tableId === 'admin_table') {
            let tdEdit = document.createElement('td')

            let editButton = document.createElement('button')
            editButton.classList.add("btn", "btn-info")
            editButton.setAttribute("data-toggle", "modal")
            editButton.setAttribute("data-target", "#edit_modal")
            editButton.textContent = "Edit"
            editButton.id = `edit_button_${user['user_id']}`
            editButton.addEventListener('click', (event) => {
                deleteEditButtonGetUser(event.target.id, 'edit')
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
                deleteEditButtonGetUser(event.target.id, 'delete')
            }, false)

            tdDelete.appendChild(deleteButton)

            row.appendChild(tdEdit)
            row.appendChild(tdDelete)
        }

        tbody.appendChild(row)

        index++
    }
}

function clearTable(tableId) {
    const tbody = document.querySelector(`.${tableId}_tbody`)
    let elements = tbody.getElementsByTagName('tr')
    while (elements[0]) elements[0].parentNode.removeChild(elements[0])
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

function deleteEditButtonGetUser(id, mode) {
    let numberId = id.split(`${mode}_button_`)[1]
    fetch(`http://localhost:8080/api/users/${numberId}`)
        .then(res => {
            return res.json()
        })
        .then(data => {
            let user = data
            console.log('UserForDeleteEdit', user)
            deleteEditButtonFillInputs(user, numberId, mode)
        })
}

function deleteEditButtonFillInputs(user, id, mode) {

    document.querySelector('#id_input_' + `${mode}`).value = user['id']
    document.querySelector('#first_name_input_' + `${mode}`).value = user['name']
    document.querySelector('#last_name_input_' + `${mode}`).value = user['lastname']
    document.querySelector('#age_input_' + `${mode}`).value = user['yearOfBirth']
    document.querySelector('#email_input_' + `${mode}`).value = user['username']
    let roles = user['roles']
    if (mode === 'delete') {
        fillRolesSelectorAdd(roles, 'role_select_' + `${mode}`)
    }

    let currentEventListener = null
    let button = document.querySelector('#button_' + `${mode}`)
    if (mode === 'edit'){
        button.removeEventListener('click', editButtonEventListener, false) // ?????????????? EventListeners
        currentEventListener = () => editButtonRequest()
        editButtonEventListener = currentEventListener
    } else if (mode === 'delete') {
        button.removeEventListener('click', deleteButtonEventListener, false) // ?????????????? EventListeners
        currentEventListener = () => deleteButtonRequest(id)
        deleteButtonEventListener = currentEventListener
    }

    console.log('Button', button)
    button.addEventListener('click', currentEventListener, false)
}

function deleteButtonRequest(id) {
    fetch(`http://localhost:8080/api/users/${id}`, {
        method: 'DELETE'
    }).then(() => getUsersAndFillTable())
}

function editButtonRequest() {
    let requestBody = {}

    // ???????????????????? requestBody
    requestBody['user_id'] = document.querySelector('#id_input_edit').value
    requestBody['name'] = document.querySelector('#first_name_input_edit').value
    requestBody['lastname'] = document.querySelector('#last_name_input_edit').value
    requestBody['yearOfBirth'] = document.querySelector('#age_input_edit').value
    requestBody['username'] = document.querySelector('#email_input_edit').value
    requestBody['password'] = document.querySelector('#password_input_edit').value

    requestBody['roles'] = []
    let selectElement = document.querySelector('#role_select_edit')
    let selectedValues = Array.from(selectElement.selectedOptions)
        .map(option => option.value)
    for (let selectedValue of selectedValues) {
        requestBody['roles'].push({
            'roleType': selectedValue
        })
    }

    fetch("http://localhost:8080/api/users", {
        method: 'PUT',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(requestBody)
    })
        .then(res => res.json())
        .then(data => console.log(data))
        .then(() => getUsersAndFillTable())
        .then(() => fillHeadingAndUserTable())
}

function fillHeading(currentUser) {
    const heading = document.querySelector(`.heading_info`)
    let spanEmail = document.createElement('span')
    spanEmail.classList.add('font-weight-bold')
    spanEmail.textContent = `${currentUser['username']}`
    let spanRoles = document.createElement('span')
    spanRoles.textContent = ` with roles: ${convertRolesToString(currentUser['roles'])}`
    let elements = heading.getElementsByTagName('span')
    while (elements[0]) elements[0].parentNode.removeChild(elements[0])
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

    // ???????????????????? requestBody
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
        .then(() => getUsersAndFillTable())
}

function fillRolesSelectorAdd(roles, id) {
    const selector = document.querySelector(`#${id}`)

    let elements = selector.getElementsByTagName('option')
    while (elements[0]) elements[0].parentNode.removeChild(elements[0])

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
            fillRolesSelectorAdd(allRoles, 'role_select_edit')
        })
}




getUsersAndFillTable()
fillHeadingAndUserTable()

document.querySelector('#submit_button_add').addEventListener('click', () => {

    addNewUser()

    // ???????????????????????? ???? ?????????????? UsersTable

    const navItemAllUsers = document.querySelector('#all_users_tab')
    const navItemNewUser = document.querySelector('#new_user-tab')

    const tabPaneAllUsers = document.querySelector('#all_users')
    const tabPaneNewUser = document.querySelector('#new_user')

    navItemAllUsers.classList.add('active')
    navItemNewUser.classList.remove('active')
    navItemAllUsers.setAttribute('area-selected','true')
    navItemNewUser.setAttribute('area-selected','false')

    tabPaneAllUsers.classList.add('show','active')
    tabPaneNewUser.classList.remove('show','active')
}, false)

getAllRoles()
