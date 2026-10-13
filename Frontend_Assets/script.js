document.querySelector('form').addEventListener('submit', async (e) => {
  e.preventDefault();
  
  // Safely capture selected radio button for gender
  const genderInput = document.querySelector('[name="gnd"]:checked');

const payload = {
  firstName: document.querySelector('[name="t1"]').value,
  lastName: document.querySelector('[name="t2"]').value,
  age: parseInt(document.querySelector('[name="n1"]').value),
  gender: genderInput ? genderInput.value : '',
  birthday: document.querySelector('[name="dt"]').value,
  email: document.querySelector('[name="t3"]').value,
  phoneNumber: document.querySelector('[name="t4"]').value,
  personalId: document.querySelector('[name="t5"]').value,
  career: document.querySelector('[name="sel"]').value
};

  try {
    const response = await fetch('https://ebn765kukd.execute-api.us-east-2.amazonaws.com/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (response.ok) {
      
      window.location.href = 'result.html';
    } else {
      alert('Error submitting form. Check backend logs.');
    }
  } catch (err) {
    console.error('Fetch error:', err);
  }
});