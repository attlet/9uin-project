import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import NotFound from './pages/NotFoundView';
import JoinView from './pages/Join/JoinView';
import JoinSuccessView from './pages/Join/JoinSuccessView';
import ProjectView from './pages/Project/ProjectView';
import StudyView from './pages/Study/StudyView';
import IDfoundView from './pages/User/IDFound/IDfoundView';
import AddPostView from './pages/AddPostView';
import MyPageView from './pages/User/MyPage/MyPageView';
import PWfoundView from './pages/User/PWfound/PWfoundView';
import PWChangeView from './pages/User/PWChange/PWChangeView';
import ClipView from './pages/User/ClipView';
import HomeView from './pages/HomeView';
import AssignView from './pages/AssignView';
import PostDetailView from './pages/PostDetailView';
import LoginView from './pages/User/Login/LoginView';
import RecruitStatusView from './pages/User/RecruitStatusView';
import ModifyPostView from './pages/ModifyPostView';
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';
import rootReducer from './modules';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import ProtectedRoute from './router/ProtectedRoute';
import { persistStore, persistReducer } from 'redux-persist';
import storageSession from 'redux-persist/lib/storage/session';
import { PersistGate } from 'redux-persist/integration/react';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: '/', element: <HomeView /> },
      { path: '/project', element: <ProjectView /> },
      { path: '/study', element: <StudyView /> },
      { path: '/postDetail/:board_id', element: <PostDetailView /> },
      {
        path: '/Addpost',
        element: (
          <ProtectedRoute>
            <AddPostView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/modifyPost/:board_id',
        element: (
          <ProtectedRoute>
            <ModifyPostView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/postDetail/assign/:post',
        element: (
          <ProtectedRoute>
            <AssignView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/mypage',
        element: (
          <ProtectedRoute>
            <MyPageView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/mypage/recruit',
        element: (
          <ProtectedRoute>
            <RecruitStatusView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/mypage/addPost',
        element: (
          <ProtectedRoute>
            <AddPostView />
          </ProtectedRoute>
        ),
      },
      {
        path: '/mypage/clip',
        element: (
          <ProtectedRoute>
            <ClipView />
          </ProtectedRoute>
        ),
      },
      { path: '/user/login', element: <LoginView /> },
      { path: '/user/idFound', element: <IDfoundView /> },
      { path: '/user/pwFound', element: <PWfoundView /> },
      { path: '/user/pwChange', element: <PWChangeView /> },
      { path: '/join', element: <JoinView /> },
      { path: '/joinSuccess', element: <JoinSuccessView /> },
    ],
  },
]);

const persistConfig = {
  key: 'root',
  storage: storageSession,
};
const persistedReducer = persistReducer(persistConfig, rootReducer);

// const store = createStore(
//   rootReducer,
//   composeWithDevTools(applyMiddleware(thunk))
// );

const store = createStore(
  persistedReducer,
  composeWithDevTools(applyMiddleware(thunk))
);
const persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <RouterProvider router={router} />
      </PersistGate>
    </Provider>
  </React.StrictMode>
);

reportWebVitals();
