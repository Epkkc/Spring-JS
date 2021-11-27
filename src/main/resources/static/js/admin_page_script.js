function fillTable(data, tableId) {
    console.log('CurrentData', data)
    const tbody = document.querySelector(`.${tableId}_tbody`)
    for (let user of data) {
        let row = document.createElement('tr')

        const roles = user['roles'] // Массив ролей
        console.log('CurrentUserRoles', roles)
        const rolesString = convertRolesToString(roles)

        row.innerHTML = `
            <td>${user['user_id']}</td>
            <td>${user['name']}</td>
            <td>${user['lastname']}</td>
            <td>${user['yearOfBirth']}</td>
            <td>${user['username']}</td>
            <td>${rolesString.length === 0 ? 'NO ROLES' : rolesString}</td>`

        tbody.appendChild(row)
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

let allUsers
let currentUser

fetch("http://localhost:8080/api/users")
    .then(res => {
        return res.json()
    })
    .then(data => {
        allUsers = data
        console.log('AllUsers', data)
        // for (let i = 0; i < allUsers.length; i++) {
        //     console.log(allUsers[i])
        // }

        fillTable(allUsers, "admin_table")
    })

fetch("http://localhost:8080/api/current-user")
    .then(res => {
        return res.json()
    })
    .then(data => {
        currentUser = data
        console.log('CurrentUser', currentUser)
        fillHeading(currentUser)
        fillTable([currentUser], "user_table")
    })