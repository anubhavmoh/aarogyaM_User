#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pandas as pd
import numpy as np
from sklearn.linear_model import LogisticRegression
import pickle


# In[3]:


logreg=LogisticRegression()


# In[4]:


data=pd.read_csv("cancer.csv")


# In[5]:


data.drop(["Unnamed: 32"],axis="columns",inplace=True)


# In[6]:


data.drop(["id"],axis="columns",inplace=True)


# In[7]:


a=pd.get_dummies(data["diagnosis"])


# In[10]:


cancer=pd.concat([data,a],axis="columns")
cancer.drop(["diagnosis","B"],axis="columns",inplace=True)
cancer.rename(columns={"M":"Malignant/Benign"},inplace=True)


# In[11]:


y=cancer[["Malignant/Benign"]]
X=cancer.drop(["Malignant/Benign"],axis="columns")
print(X.shape[1])


# In[12]:


X=np.array(X)
y=np.array(y)


# In[13]:


logreg.fit(X,y.reshape(-1,))


# In[15]:


pickle.dump(logreg,open('cancer.pkl','wb'))


# In[ ]:




