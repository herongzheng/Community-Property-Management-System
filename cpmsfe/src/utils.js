const SERVER_ORIGIN = "";

const residentLoginUrl = `${SERVER_ORIGIN}/authenticate/resident`;
const hostLoginUrl = `${SERVER_ORIGIN}/authenticate/hoa`;

export const login = (credential, asHost) => {
  const loginUrl = asHost ? hostLoginUrl : residentLoginUrl;

  return fetch(loginUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credential),
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to log in");
    }
    return response.json(); // Extract the token from the server response.
  });
};

export const changePassword = (data) => {
  const authToken = localStorage.getItem("authToken");
  const changePasswordUrl = `${SERVER_ORIGIN}/change_password`;
  console.log(data);
  return fetch(changePasswordUrl, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${authToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      old_password: data.oldPassword,
      new_password: data.newPassword,
      last_name: data.lastName,
    }),
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Failed to change password");
    }
    return response.text();
  });
};

export const getResidents = () => {
  const authToken = localStorage.getItem("authToken");
  const residentsUrl = `${SERVER_ORIGIN}/manage/residents`;
  return fetch(residentsUrl, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Failed to retrieve residents");
    }
    return response.json();
  });
};

export const createResident = (username) => {
  const authToken = localStorage.getItem("authToken");
  const createResidentUrl = `${SERVER_ORIGIN}/manage/residents/new_resident/${username}`;
  return fetch(createResidentUrl, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Failed to create resident");
    }
    return response.text();
  });
};

export const getIssues = () => {
  // const authToken = localStorage.getItem("authToken");
  const authToken =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSb29tMDAzIiwiZXhwIjoxNjk0ODA0OTA4LCJpYXQiOjE2OTQ2MzIxMDh9.tW3jY_mdGGzqQ0u4Gk4iLDs_2Vfnn3QSCiSuuSPNJZQ";
  const listIssueUrl = `${SERVER_ORIGIN}/issues`;

  return fetch(listIssueUrl, {
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get issue list");
    }

    return response.json();
  });
};

export const createIssue = (issueData) => {
  const authToken = localStorage.getItem("authToken");
  const createIssueUrl = `${SERVER_ORIGIN}/issues/create`;

  return fetch(createIssueUrl, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
    body: issueData,
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to create the issue");
    }
  });
};

export const confirmIssue = (issueId) => {
  const authToken = localStorage.getItem("authToken");
  const confirmIssueUrl = `${SERVER_ORIGIN}/issues/confirm/${issueId}`;

  return fetch(confirmIssueUrl, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to confrim the issue");
    }
  });
};

export const closeIssue = (issueId) => {
  const authToken = localStorage.getItem("authToken");
  const closeIssueUrl = `${SERVER_ORIGIN}/issues/close/${issueId}`;

  return fetch(closeIssueUrl, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to close the issue");
    }
  });
};
