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
    body: JSON.stringify(data),
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
  const authToken = localStorage.getItem("authToken");
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

export const chat = () => {
  const authToken = localStorage.getItem("authToken");
  const listChatsUrl = `${SERVER_ORIGIN}/chat`;

  return fetch(listChatsUrl, {
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get post list");
    }

    return response.json();
  });
};

export const createChat = async (postData) => {
  const authToken = localStorage.getItem("authToken");
  const createChatUrl = `${SERVER_ORIGIN}/chat/create`;

  // Creating URLSearchParams object from postData
  const params = new URLSearchParams();
  params.append("content", postData.content);
  params.append("posted_time", postData.posted_time);
  if (postData.reply_to !== null) {
    params.append("reply_to", postData.reply_to);
  }

  try {
    const response = await fetch(createChatUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
        "Content-Type": "application/x-www-form-urlencoded", // Adjusted the content type
      },
      body: params, // Now we send params instead of JSON stringified postData
    });

    if (response.status === 400) {
      throw new Error("Bad Request: Check the request body");
    } else if (response.status === 500) {
      throw new Error("Internal Server Error: Contact the administrator");
    } else if (response.status !== 200) {
      throw new Error("Failed to create chat post");
    }

    const updatedPosts = await response.json();
    return updatedPosts;
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error for further handling
  }
};

export const deleteChat = (postId) => {
  const authToken = localStorage.getItem("authToken");
  const deleteChatUrl = `${SERVER_ORIGIN}/chat/delete/${postId}`;
  return fetch(deleteChatUrl, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to delete comment");
    }
  });
};

export const likeChat = async (postId) => {
  const authToken = localStorage.getItem("authToken");
  const likeChatUrl = `${SERVER_ORIGIN}/chat/like/${postId}`;

  try {
    const response = await fetch(likeChatUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (response.ok) {
      const text = await response.text();
      if (text) {
        const responseData = JSON.parse(text);
        return responseData.likeCount;
      } else {
        // Handle empty response (maybe return a default value or handle it upstream)
        return null;
      }
    } else {
      throw new Error("Failed to like chat");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error for further handling
  }
};

export const unlikeChat = async (postId) => {
  const authToken = localStorage.getItem("authToken");
  const unlikeChatUrl = `${SERVER_ORIGIN}/chat/unlike/${postId}`;

  try {
    const response = await fetch(unlikeChatUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (response.ok) {
      const text = await response.text();
      if (text) {
        const responseData = JSON.parse(text);
        return responseData.likeCount;
      } else {
        // Handle empty response (maybe return a default value or handle it upstream)
        return null;
      }
    } else {
      throw new Error("Failed to like chat");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error for further handling
  }
};

export const replyChat = async (postId, replyData) => {
  const authToken = localStorage.getItem("authToken");
  const replyChatUrl = `${SERVER_ORIGIN}/chat/reply/${postId}`;

  // Convert replyData to URLSearchParams to send data as form parameters
  const params = new URLSearchParams();
  params.append("content", replyData.content);
  params.append("posted_time", replyData.posted_time);
  if (replyData.reply_to !== undefined) {
    params.append("reply_to", replyData.reply_to);
  }

  try {
    const response = await fetch(replyChatUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
      body: params, // use params here instead of JSON body
    });

    if (response.status === 200) {
      // Reply was successful, you might return the list of replies from the response
      const responseData = await response.json();
      return responseData;
    } else if (response.status === 400) {
      throw new Error("Bad Request: Check the request body");
    } else if (response.status !== 200) {
      throw new Error("Failed to reply to chat");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const stickChat = async (postId) => {
  const authToken = localStorage.getItem("authToken");
  const topChatUrl = `${SERVER_ORIGIN}/chat/top/${postId}`;

  try {
    const response = await fetch(topChatUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (response.status === 200) {
      // Successful response, parse and return the top chat posts
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error("Failed to fetch top chat posts");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const unstickChat = async (postId) => {
  const authToken = localStorage.getItem("authToken");
  const untopChatUrl = `${SERVER_ORIGIN}/chat/untop/${postId}`;

  try {
    const response = await fetch(untopChatUrl, {
      method: "POST", // You may need to change this to "DELETE" or another appropriate HTTP method
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error("Failed to untop chat");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const showReplyChat = (postId) => {
  const authToken = localStorage.getItem("authToken");
  const showReplyChatUrl = `${SERVER_ORIGIN}/chat/show_replies/${postId}`;

  return fetch(showReplyChatUrl, {
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get reply list");
    }

    return response.json();
  });
};

export const likedChat = () => {
  const authToken = localStorage.getItem("authToken");
  const likedChatUrl = `${SERVER_ORIGIN}/chat/liked`;

  return fetch(likedChatUrl, {
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    if (response.status !== 200) {
      throw Error("Fail to get reply list");
    }

    return response.json();
  });
};

export const packages = async () => {
  try {
    const authToken = localStorage.getItem("authToken");

    if (!authToken) {
      throw new Error("Authentication token is not available");
    }

    const packagesUrl = `${SERVER_ORIGIN}/packages`;

    const response = await fetch(packagesUrl, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch packages: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const createPackages = async (packageData) => {
  try {
    const authToken = localStorage.getItem("authToken");
    if (!authToken) {
      throw new Error("Authentication token is not available");
    }
    const createPackagesUrl = `${SERVER_ORIGIN}/packages/create`;

    const response = await fetch(createPackagesUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(packageData),
    });

    if (response.status === 400) {
      throw new Error("Bad Request: Check the request body");
    } else if (response.status === 500) {
      throw new Error("Internal Server Error: Contact the administrator");
    } else if (response.status !== 200) {
      throw new Error("Failed to create package");
    }
    // const updatedPosts = await response.json();
    // return updatedPosts;

    const responseBody = await response.text();
    if (responseBody) {
      const updatedPosts = JSON.parse(responseBody);
      return updatedPosts;
    } else {
      return null; // or an appropriate default value
    }
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const pickupPackage = async (packageId) => {
  try {
    const authToken = localStorage.getItem("authToken");

    if (!authToken) {
      throw new Error("Authentication token is not available");
    }

    const pickupPackagesUrl = `${SERVER_ORIGIN}/packages/pickup/${packageId}`;

    const response = await fetch(pickupPackagesUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (response.status === 400) {
      throw new Error("Bad Request: Check the request body");
    } else if (response.status === 500) {
      throw new Error("Internal Server Error: Contact the administrator");
    } else if (response.status !== 200) {
      throw new Error("Failed to create chat post");
    }
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

export const messages = async () => {
  try {
    const authToken = localStorage.getItem("authToken");

    if (!authToken) {
      throw new Error("Authentication token is not available");
    }

    const messageUrl = `${SERVER_ORIGIN}/messages`;

    const response = await fetch(messageUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch messages: ${response.statusText}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error for further handling
  }
};

export const getUnPickedUpCount = async () => {
  try {
    const authToken = localStorage.getItem("authToken");

    if (!authToken) {
      throw new Error("Authentication token is not available");
    }

    const unreadMessageCountUrl = `${SERVER_ORIGIN}/messages/unpicked_count`;

    const response = await fetch(unreadMessageCountUrl, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (!response.ok) {
      throw new Error(
        `Failed to fetch unread message count: ${response.statusText}`
      );
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error:", error);
    throw error; // Rethrow the error for further handling
  }
};
