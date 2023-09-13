const SERVER_ORIGIN = "";

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
