#!/usr/bin/env python
# coding: utf-8

# In[56]:


import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn import svm
import pickle


# In[57]:


diabetes_dataset = pd.read_csv('diabetes.csv') 


# In[58]:


diabetes_dataset


# In[59]:


# number of rows and Columns in this dataset
diabetes_dataset.shape


# In[60]:


diabetes_dataset.shape


# In[61]:


diabetes_dataset['Outcome'].value_counts()


# In[62]:


X = diabetes_dataset.drop(columns = 'Outcome', axis=1)
Y = diabetes_dataset['Outcome']


# In[63]:


scaler = StandardScaler()


# In[64]:


scaler.fit(X)


# In[65]:


standardized_data = scaler.transform(X)


# In[66]:


X = standardized_data
Y = diabetes_dataset['Outcome']


# In[ ]:





# In[67]:


classifier = svm.SVC(kernel='linear')


# In[68]:


classifier.fit(X, Y)


# In[69]:


X_train_prediction = classifier.predict(X)
training_data_accuracy = accuracy_score(X_train_prediction, Y)


# In[70]:


print('Accuracy score of the training data : ', training_data_accuracy)


# In[71]:


input_data = (5,166,72,19,175,25.8,0.587,51)

# changing the input_data to numpy array
input_data_as_numpy_array = np.asarray(input_data)

# reshape the array as we are predicting for one instance
input_data_reshaped = input_data_as_numpy_array.reshape(1,-1)

# standardize the input data
std_data = scaler.transform(input_data_reshaped)
print(std_data)

prediction = classifier.predict(std_data)
print(prediction)

if (prediction[0] == 0):
  print('The person is not diabetic')
else:
  print('The person is diabetic')


# In[73]:


pickle.dump(classifier,open('diabetes.pkl','wb'))


# In[ ]:




